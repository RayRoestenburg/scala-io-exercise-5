package com.xebia.exercise5

import scala.concurrent.ExecutionContext

import akka.actor.{Actor, Props, ActorRef, ActorRefFactory}

import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport._
import spray.testkit.Specs2RouteTest

import org.specs2.mutable.Specification

class ReceptionistSpec extends Specification
                          with Specs2RouteTest {

  trait TestCreationSupport extends CreationSupport {
    def createChild(props: Props, name: String): ActorRef = {
      if(name == ReverseActor.name) {
        system.actorOf(Props[FakeReverseActor], name)
      } else system.actorOf(props, name)
    }

    def getChild(name: String): Option[ActorRef] = None
  }

  val subject = new ReverseRoute with L33tRoute with TestCreationSupport {
    implicit def actorRefFactory: ActorRefFactory = system
    implicit def executionContext = system.dispatcher
  }

  "The Receptionist" should {
    "Respond with a JSON response that contains a reversed string value" in {

      Post("/reverse", ReverseRequest("some text to reverse")) ~> subject.reverseRoute ~> check {
        status === StatusCodes.OK
        val response = entityAs[ReverseResponse]
        response.value must beEqualTo("esrever ot txet emos")
        response.isPalindrome must beFalse
      }

      Post("/reverse", ReverseRequest("akka")) ~> subject.reverseRoute ~> check {
        status === StatusCodes.OK
        val response = entityAs[ReverseResponse]
        response.value must beEqualTo("akka")
        response.isPalindrome must beTrue
      }
    }

    "Respond with a JSON response that contains a reversed string value" in {
      Post("/l33t", L33tRequest("somestuff")) ~> subject.l33tRoute ~> check {
        status === StatusCodes.OK
        val response = entityAs[L33tResponse]
        response.value must beEqualTo("50M357UFF")
      }
    }
  }
}

class FakeReverseActor extends Actor {
  import ReverseActor._

  def receive = {
    case Reverse("akka") => sender ! PalindromeResult
    case Reverse("some text to reverse") => sender ! ReverseResult("esrever ot txet emos")
  }
}
