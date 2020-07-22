package de.htwg.signalk.parser

object RuleConstants {
  val DistanceType = List("Marker", "LogEntry", "Photo", "Waypoint")
  val SpeedType = List("SOG", "STW", "AWS", "TWS", "VMG")
  val AngleType = List("TWA", "TWD", "AWA", "AWD", "HDG", "COG", "CTW")
  val PercentType = List("Battery", "Fuel", "Performance")
  val TempType = List("Air", "Water", "Engine")

  val OneArg = List("below", "above")
  val TwoArg = List("outside", "between")

  val SoundType = List("Alarm", "Beep", "Bell", "Horn", "Whistle")
  val SoundAmount = List("until checked", "5 times", "max 30 sec", "max 2 min")

  val SoundAmountFailureMessage = s"Not a valid sound amount! Try ${toMessage(SoundAmount)}!"
  val SoundTypeFailureMessage = s"Not a valid sound type! Try ${toMessage(SoundType)}!"

  private def toMessage(list: List[String]) = list.map(str => "\"" + str + "\"").mkString(", ")
}
