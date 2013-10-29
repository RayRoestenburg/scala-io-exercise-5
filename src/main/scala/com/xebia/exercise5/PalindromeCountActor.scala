package com.xebia
package exercise5

import akka.actor.{Props, Actor}
import PalindromeCountActor.{NrOfPalindromes, CountPalindromes}
import ReverseActor.PalindromeFound

object PalindromeCountActor {
  def props = Props[PalindromeCountActor]
  def name = "palindrome-counter"

  case object CountPalindromes
  case class NrOfPalindromes(count:Int)
}

class PalindromeCountActor extends Actor {
  //TODO subscribe this actor to PalindromeFound case class

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
