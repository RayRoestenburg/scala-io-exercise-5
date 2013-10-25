package com.xebia.exercise5

import akka.actor.{ActorRef, Actor, Props}
import com.xebia.exercise5.ReverserFactory.AsyncReverseFunction
import scala.concurrent.duration._
import scala.util.{Success, Failure}

object ReverseActor {
  def props = Props[ReverseActor]
  def name = "reverser"

  sealed trait Result
  case class Reverse(value:String)

  case class ReverseResult(value:String) extends Result

  case object PalindromeResult extends Result

  case object Init

  case object NotInitialized extends Result

  //TODO create a case class ReverseInitialized which contains the ActorRef of the ReverseActor

  //TODO create a case object PalindromeFound
}

class ReverseActor extends Actor {
  import ReverseActor._

  import context._

  self ! Init

  def receive = uninitialized

  def uninitialized:Receive = {
    case Init => ReverserFactory.loadReverser.onComplete {
      case Success(reverse) =>
        context.become(initialized(reverse))
        // TODO publish the ReverseInitialized message containing the ActorRef of this actor

      case Failure(e)   => context.system.scheduler.scheduleOnce(1 seconds, self, Init)
    }
    case Reverse(value) => sender ! NotInitialized
  }

  def initialized(reverse: AsyncReverseFunction):Receive = {
    case Reverse(value) =>
      val theSender = sender

      reverse(value).map { reversed =>
        if(reversed == value) {
          theSender ! PalindromeResult
          println("palindrome in reverse:"+self + " " + self.path)
          // TODO publish the PalindromeFound message
        }
        else theSender ! ReverseResult(reversed)
      }
  }

}
