package com.xebia
package exercise5

import spray.testkit.Specs2RouteTest
import org.specs2.mutable.Specification
import TestSupport._

class ReverseActorSpec extends Specification
                          with Specs2RouteTest {
  sequential

  "The ReverseActor" should {
    "Reverse a string that it receives if it is not a Palindrome" in new AkkaTestkitContext() {
      import ReverseActor._
      system.eventStream.subscribe(testActor, classOf[ReverseInitialized])

      val reverseActor = system.actorOf(props, name)
      import akka.pattern.ask

      expectMsg(ReverseInitialized(reverseActor))

      reverseActor ! Reverse("reverse this!")

      expectMsg(ReverseResult("!siht esrever"))

      expectNoMsg()

    }

    "Not reverse a string but return a PalindromeResult if the reversal has no effect" in new AkkaTestkitContext() {
      import ReverseActor._

      system.eventStream.subscribe(testActor, classOf[ReverseInitialized])

      val reverseActor = system.actorOf(props, name)

      expectMsg(ReverseInitialized(reverseActor))

      reverseActor ! Reverse("akka")

      expectMsg(PalindromeResult)

      expectNoMsg()

    }
  }
}
