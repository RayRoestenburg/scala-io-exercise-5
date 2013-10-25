package com.xebia.exercise5

import org.specs2.mutable.Specification
import TestSupport._
import spray.testkit.Specs2RouteTest

class ReverseActorSpec extends Specification with Specs2RouteTest {

  "The ReverseActor" should {
    "Reverse a string that it receives if it is not a Palindrome" in new AkkaTestkitContext() {
      import ReverseActor._

      val reverseActor = system.actorOf(props, name)
      import akka.pattern.ask

      reverseActor ! Reverse("reverse this!")

      expectMsg(NotInitialized)

      // We will fix this later
      Thread.sleep(600)

      reverseActor ! Reverse("reverse this!")

      expectMsg(ReverseResult("!siht esrever"))

      expectNoMsg()

    }

    "Not reverse a string but return a PalindromeResult if the reversal has no effect" in new AkkaTestkitContext() {
      import ReverseActor._

      val reverseActor = system.actorOf(props, name)

      reverseActor ! Reverse("akka")
      expectMsg(NotInitialized)

      // We will fix this later
      Thread.sleep(600)

      reverseActor ! Reverse("akka")

      expectMsg(PalindromeResult)

      expectNoMsg()

    }
  }
}
