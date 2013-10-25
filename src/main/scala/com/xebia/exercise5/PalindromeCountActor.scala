package com.xebia.exercise5

import akka.actor.{Props, Actor}
import com.xebia.exercise5.PalindromeCountActor.{NrOfPalindromes, CountPalindromes}
import com.xebia.exercise5.ReverseActor.PalindromeFound

object PalindromeCountActor {
  def props = Props[PalindromeCountActor]
  def name = "palindrome-counter"
  case object CountPalindromes
  case class NrOfPalindromes(count:Int)
}

class PalindromeCountActor extends Actor {
  //TODO subscribe this actor to PalindromeFound case class
  context.system.eventStream.subscribe(self, classOf[PalindromeFound])

  var count = 0
  
  def receive = {

    case PalindromeFound(value) =>
      count = count + 1
      if(count % 10 == 0) println(s"$count Palindromes found!")

    case CountPalindromes =>
      sender ! NrOfPalindromes(count)
  }

  override def postStop(): Unit = {
    context.system.eventStream.unsubscribe(self)
    super.postStop()
  }
}
