package de.htwg.signalk.parser

object RuleConstants {
  // Trigger
  val TriggerType = List("value of", "distance to", "time", "timer")

  val DistanceType = List("Marker", "LogEntry", "Photo", "Waypoint")
  val DistanceExampleValues = List("100 m", "200 m", "500 m", "1 nm", "2 nm", "5 nm")

  val SpeedType = List("SOG", "STW", "AWS", "TWS", "VMG")
  val SpeedExampleArgs = List("0 kn", "1 kn", "2 kn", "5 kn", "10 kn", "20 kn", "40 kn", "60 kn")

  val AngleType = List("TWA", "TWD", "AWA", "AWD", "HDG", "COG", "CTW")
  val AngleExampleArgs = List("0 °", "30 °", "60 °", "90 °", "120 °", "150 °", "180 °", "210 °", "240 °", "270 °", "300 °", "330 °")

  val PercentType = List("Battery", "Fuel", "Performance")
  val PercentExampleArgs = List("0 %", "10 %", "20 %", "50 %", "80 %", "100 %")

  val TempType = List("Air", "Water", "Engine")
  val TempExampleArgs = List("-10 °C", "0 °C", "10 °C", "20 °C", "30 °C", "40 °C", "50 °C", "80 °C", "100 °C")

  val DepthType = List("Depth")
  val DepthExampleArgs = List("2 m", "5 m", "10 m", "25 m", "50 m", "100 m")

  val OneArgOp = List("below", "above")
  val TwoArgOp = List("outside", "between")

  // Action
  val ActionType = List("sound", "warn", "log", "send", "activate", "deactivate", "reset", "restart")

  val SoundType = List("Alarm", "Beep", "Bell", "Horn", "Whistle")
  val SoundExampleArgs = List("once", "twice", "three times", "max 20 sec", "max 1 min", "until checked")

  val SoundAmountFailureMessage = s"Not a valid sound amount! Try ${toHint(SoundExampleArgs)}!"
  val SoundTypeFailureMessage = s"Not a valid sound type! Try ${toHint(SoundType)}!"

  val SendRecipient = List("me", "Skipper", "Obman", "Service")

  private def toHint(list: List[String]) = list.map(str => "\"" + str + "\"").mkString(", ")
}
