package de.htwg.signalk.parser

import squants.time.{Minutes, Seconds}
import RuleConstants._

import scala.util.parsing.combinator.RegexParsers

trait ActionParser extends RegexParsers {
  val numberConversion = Map("one" -> 1, "two" -> 2, "three" -> 3, "four" -> 4,"five" -> 5,"six" -> 6,"seven" -> 7,"eight" -> 8,"nine" -> 9, "ten" -> 10)
  def minutesMin = "[0-9]*".r <~ "min" ^^ (minutes => Minutes(minutes.toInt))
  def secondsSec = "[0-9]*".r <~ "sec" ^^ (seconds => Seconds(seconds.toInt))
  def maxMinutes= "max" ~> minutesMin ^^ (minute => SoundMax(minute).asInstanceOf[SoundAmount])
  def maxSeconds = "max" ~> secondsSec  ^^ (seconds => SoundMax(seconds).asInstanceOf[SoundAmount])
  def maxTimes = maxMinutes | maxSeconds
  def nTimes = "[0-9]*".r <~ "times" ^^ (n => SoundRepeat(n.toInt).asInstanceOf[SoundAmount])
  def nTimesWord = ("one" | "two" | "three" | "four" | "five" | "six" | "seven" | "eight" | "nine" | "ten") ~ "times" ^^ { case n ~ _ => SoundRepeat(numberConversion(n)).asInstanceOf[SoundAmount] }
  def once = "once" ^^ (_ => SoundRepeat(1).asInstanceOf[SoundAmount])
  def twice = "twice" ^^ (_ => SoundRepeat(2).asInstanceOf[SoundAmount])
  def amountOfTimes = once | twice | nTimes | nTimesWord
  def untilChecked = "until checked" ^^ (_ => SoundUntilChecked().asInstanceOf[SoundAmount])
  def soundAmount = (amountOfTimes | maxTimes | untilChecked).withFailureMessage(SoundAmountFailureMessage)
  def soundType = ("Alarm" | "Beep" | "Bell" | "Horn" | "Whistle").withFailureMessage(SoundTypeFailureMessage)
  def soundClause = "sound" ~> soundType ~ soundAmount ^^ { case soundType ~ soundExp => SoundAction(soundType, soundExp) }

  def warnMessage = ".*".r
  def warnClause = "warn" ~> warnMessage ^^ (warnMessage => WarnAction(warnMessage))

  def sendRecipient = "me" | "Skipper" | "Obman" | "Service"
  def sendMessage = ".* to".r
  def sendClause = "send" ~> sendMessage ~ sendRecipient ^^ { case sendMessage ~ sendRecipient => SendAction(sendRecipient, sendMessage.dropRight(3)) }

  def logMessage = ".*".r
  def logClause = "log" ~> logMessage ^^ (logMessage => LogAction(logMessage))

  def minutes = ("[0-5]?[0-9]".r <~ ":") ^^ { string => Minutes(string.toInt) }
  def seconds = "[0-5][0-9]".r ^^ { string => Seconds(string.toInt) }
  def actionTimeExp = minutes ~ seconds ^^ { case minutes ~ seconds => minutes + seconds }
  def reactivateExp = "after" ~> (actionTimeExp | minutesMin | secondsSec) ^^ { time => time }
  def reactivateClause = "reactivate" ~> opt(reactivateExp) ^^ (reactivateDelay => ReactivateAction(reactivateDelay))

  def deactivateClause = "deactivate" ^^ { _ => DeactivateAction() }

  def resetTarget = "Timer" | "Daylog"
  def resetClause = "reset" ~> resetTarget ^^ (resetTarget => ResetAction(resetTarget))

  def sentenceStart = ("," ~ "then") | "and"
  def action = sentenceStart ~> (soundClause | warnClause | sendClause | logClause | reactivateClause | deactivateClause) ^^ (action => action.asInstanceOf[Action])
}
