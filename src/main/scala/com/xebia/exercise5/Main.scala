package com.xebia.exercise5

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import spray.can.Http
import spray.can.Http.Bind

object Main extends App {

  implicit val system = ActorSystem("exercise-1")

  class TheReceptionist extends Receptionist with ActorContextCreationSupport

  val receptionist = system.actorOf(Props[TheReceptionist], "receptionist")
  val settings = Settings(system)

  IO(Http) ! Bind(listener= receptionist, interface = settings.Http.Host, port = settings.Http.Port)
}

