package com.xebia
package exercise5

import java.util.concurrent.TimeUnit

import scala.concurrent.duration.FiniteDuration

import com.typesafe.config.Config

import akka.actor._

class Settings(config: Config, extendedSystem: ExtendedActorSystem) extends Extension {

  object Http {
    val Port = config.getInt("scala-io-exercise.http.port")
    val Host = config.getString("scala-io-exercise.http.host")
  }

  object L33t {
    val uppercase = config.getBoolean("scala-io-exercise.l33t.uppercase")
    val useShift = config.getBoolean("scala-io-exercise.l33t.use-shift")
  }

  val askTimeout = FiniteDuration(config.getMilliseconds("scala-io-exercise.ask-timeout"), TimeUnit.MILLISECONDS)

}


object Settings extends ExtensionId[Settings] with ExtensionIdProvider {
  override def lookup = Settings
  override def createExtension(system: ExtendedActorSystem) = new Settings(system.settings.config, system)
}
