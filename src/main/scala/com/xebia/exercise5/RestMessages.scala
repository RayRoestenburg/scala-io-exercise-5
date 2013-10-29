package com.xebia
package exercise5

import spray.json.DefaultJsonProtocol

case class ReverseRequest(value:String)

object ReverseRequest extends DefaultJsonProtocol {
  implicit val format = jsonFormat1(ReverseRequest.apply)
}

case class ReverseResponse(value:String, isPalindrome:Boolean = false)

object ReverseResponse extends DefaultJsonProtocol {
  implicit val format = jsonFormat2(ReverseResponse.apply)
}

case class L33tRequest(value:String)

object L33tRequest extends DefaultJsonProtocol {
  implicit val format = jsonFormat1(L33tRequest.apply)
}

case class L33tResponse(value:String)

object L33tResponse extends DefaultJsonProtocol {
  implicit val format = jsonFormat1(L33tResponse.apply)
}

case class PalindromeCountResponse(count:Int)

object PalindromeCountResponse extends DefaultJsonProtocol {
  implicit val format = jsonFormat1(PalindromeCountResponse.apply)
}