package com.xebia
package exercise5

import scala.concurrent.ExecutionContext

import akka.util.Timeout

import spray.routing._
import spray.httpx.SprayJsonSupport._
import spray.http.StatusCodes

import scala.util.{Failure, Success}

trait Receptionist extends HttpServiceActor
                      with ReverseRoute
                      with L33tRoute
                      with PalindromeCountRoute
                      with ActorContextCreationSupport {

  def receive = runRoute(reverseRoute ~ l33tRoute ~ palindromeCountRoute)

  implicit def executionContext = actorRefFactory.dispatcher
}

trait ReverseRoute extends HttpService
                      with CreationSupport {

  implicit def executionContext:ExecutionContext

  private val reverseActor = createChild(ReverseActor.props, ReverseActor.name)

  def reverseRoute:Route = path("reverse") {
    post {
      entity(as[ReverseRequest]) { request =>
        import scala.concurrent.duration._
        implicit val timeout = Timeout(20 seconds)
        import akka.pattern.ask

        import ReverseActor._

        val futureResponse = reverseActor.ask(Reverse(request.value)).mapTo[Result]

        onComplete(futureResponse) {
          case Success(PalindromeResult) => complete(ReverseResponse(request.value, true))
          case Success(ReverseResult(value)) => complete(ReverseResponse(value, false))
          case Success(NotInitialized) => complete(StatusCodes.ServiceUnavailable)
          case Failure(e) => complete(StatusCodes.InternalServerError)
        }
      }
    }
  }
}

trait L33tRoute extends HttpService
                   with CreationSupport {

  implicit def executionContext:ExecutionContext

  private val l33tActor = createChild(L33tActor.props, L33tActor.name)

  def l33tRoute = path("l33t") {
    post {
      entity(as[L33tRequest]) { request =>
        import scala.concurrent.duration._
        implicit val timeout = Timeout(20 seconds)
        import akka.pattern.ask

        import L33tActor._

        val futureResponse = l33tActor.ask(L33tify(request.value)).mapTo[L33tResult].map(r=> L33tResponse(r.value))
        complete(futureResponse)
      }
    }
  }
}

trait PalindromeCountRoute extends HttpService
                              with CreationSupport {

  implicit def executionContext:ExecutionContext

  private val counter = createChild(PalindromeCountActor.props, PalindromeCountActor.name)

  def palindromeCountRoute:Route = get {
    path("count-palindromes") {
      dynamic {
        import scala.concurrent.duration._
        implicit val timeout = Timeout(20 seconds)
        import akka.pattern.ask

        import PalindromeCountActor._

        val futureResponse = counter.ask(CountPalindromes).mapTo[NrOfPalindromes].map{r=>
          PalindromeCountResponse(r.count)}
        complete(futureResponse)
      }
    }
  }
}

