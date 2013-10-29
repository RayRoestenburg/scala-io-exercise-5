package com.xebia
package exercise5

import akka.actor.{Props, Actor}

object L33tActor {
  case class L33tify(value:String)
  case class L33tResult(value:String)
  def props = Props[L33tActor]
  def name = "l33t"
}

class L33tActor extends Actor {
  import L33tActor._

  val settings = Settings(context.system)

  val upper = settings.L33t.uppercase

  val useShiftDigit = settings.L33t.useShift

  val conversionMap =      Map('o'-> 0, 'i'-> 1, 'z'->2, 'e'-> 3, 'a' -> '4', 's' -> 5, 'b'-> 6, 't' -> 7, 'B'-> 8, 'g'-> 9)

  val shiftConversionMap = Map('o'-> ')', 'i'->'!', 'z'->'@', 'e'-> '#', 'a'-> '$', 's' -> '%', 'b'-> '^', 't' -> '&', 'B'-> '*', 'g'-> '(')

  def convertToDigit(value:String) = value.map(c=> conversionMap.get(c).map(number=> number.toString).getOrElse(c)).mkString("")

  def convertToShiftDigit(value:String) = value.map(c=> shiftConversionMap.getOrElse(c,c)).mkString("")

  def receive = {
    case L33tify(value) =>

      val result = if(useShiftDigit) convertToShiftDigit(value) else convertToDigit(value)

      val msg = if(upper) result.toUpperCase() else result

      sender ! L33tResult(msg)
  }
}
