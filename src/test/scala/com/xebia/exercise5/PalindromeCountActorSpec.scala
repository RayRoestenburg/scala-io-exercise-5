package com.xebia.exercise5

import org.specs2.mutable.Specification
import TestSupport._
import spray.testkit.Specs2RouteTest

class PalindromeCountActorSpec extends Specification with Specs2RouteTest {
  sequential

  "The PalindromeCountActor" should {
    "keep a countof the found palindromes it receives" in new AkkaTestkitContext() {
      import PalindromeCountActor._
      val countActor = system.actorOf(props, name)
      countActor ! CountPalindromes
      expectMsg(NrOfPalindromes(0))

      import ReverseActor._
      countActor ! PalindromeFound("akka")

      countActor ! CountPalindromes
      expectMsg(NrOfPalindromes(1))

      expectNoMsg()

    }
  }
}