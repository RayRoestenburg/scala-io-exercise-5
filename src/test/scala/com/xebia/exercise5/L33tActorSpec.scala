package com.xebia.exercise5


import spray.testkit.Specs2RouteTest
import com.xebia.exercise5.TestSupport.AkkaTestkitContext
import org.specs2.mutable.Specification
import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

class L33tActorSpec extends Specification  with Specs2RouteTest {

  // This is configured for the test
  // with fallback to the application.conf
  override def testConfigSource: String =
  """
  scala-io-exercise {

    l33t {
      uppercase = true
      use-shift = false
    }
  }
  """

  "The FormatActor" should {
    "Replace specific characters with similar looking digits" in new AkkaTestkitContext(system) {
      import L33tActor._

      val formatActor = system.actorOf(props, name)
      formatActor ! L33tify("reverse")
      expectMsg(L33tResult("R3V3R53"))
    }
  }

  // An alternate configuration is used for the next testcase
  val alternateConfigString =
   """
  scala-io-exercise {

    l33t {
      uppercase = false
      use-shift = true
    }
  }
  """

  val alternateConfig = ConfigFactory.parseString(alternateConfigString).withFallback(ConfigFactory.load())

  "The L33tActor" should {
    "Replace specific characters with similar looking digits using an alternate configuration" in new AkkaTestkitContext(ActorSystem("test",alternateConfig)) {
      import L33tActor._

      val formatActor = system.actorOf(props, name)
      formatActor ! L33tify("reverse")
      expectMsg(L33tResult("r#v#r%#"))
    }
  }

}
