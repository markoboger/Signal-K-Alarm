package de.htwg.signalk.parser

import squants.time.{Minutes, Seconds}
import RuleConstants._

import scala.util.parsing.combinator.RegexParsers

trait ActionParser extends RegexParsers {
  val numberConversion = Map("one" -> 1, "two" -> 2, "three" -> 3, "four" -> 4,"five" -> 5,"six" -> 6,"seven" -> 7,"eight" -> 8,"nine" -> 9, "ten" -> 10)
  def minutesMin = "[0-9]*".r ~ "min" ^^ { case minutes ~ _ => Minutes(minutes.toInt) }
  def secondsSec = "[0-9]*".r ~ "sec" ^^ { case seconds ~ _ => Seconds(seconds.toInt) }
  def maxMinutes= "max"~ minutesMin ^^ { case _ ~ minute => SoundMax(minute).asInstanceOf[SoundAmount] }
  def maxSeconds = "max" ~ secondsSec  ^^ { case _ ~ seconds=> SoundMax(seconds).asInstanceOf[SoundAmount] }
  def maxTimes = maxMinutes | maxSeconds
  def nTimes = "[0-9]*".r ~ "times" ^^ { case n ~ _ => SoundRepeat(n.toInt).asInstanceOf[SoundAmount] }
  def nTimesWord = ("one" | "two" | "three" | "four" | "five" | "six" | "seven" | "eight" | "nine" | "ten") ~ "times" ^^ { case n ~ _ => SoundRepeat(numberConversion(n)).asInstanceOf[SoundAmount] }
  def once = "once" ^^ { case _ => SoundRepeat(1).asInstanceOf[SoundAmount] }
  def twice = "twice" ^^ { case _ => SoundRepeat(2).asInstanceOf[SoundAmount] }
  def amountOfTimes = once | twice | nTimes | nTimesWord
  def untilChecked = "until checked" ^^ { case _ => SoundUntilChecked().asInstanceOf[SoundAmount] }
  def soundAmount = (amountOfTimes | maxTimes | untilChecked).withFailureMessage(SoundAmountFailureMessage)
  def soundType = ("Alarm" | "Beep" | "Bell" | "Horn" | "Whistle").withFailureMessage(SoundTypeFailureMessage)
  def soundClause = "sound" ~ soundType ~ soundAmount ^^ { case _ ~ soundType ~ soundExp => SoundAction(soundType, soundExp) }

  def warnMessage = ".*".r
  def warnClause = "warn" ~ warnMessage ^^ { case _ ~ warnMessage => WarnAction(warnMessage) }

  def sendRecipient = "me" | "Skipper" | "Obman" | "Service"
  def sendMessage = ".* to".r
  def sendClause = "send" ~ sendMessage ~ sendRecipient ^^ { case _ ~ sendMessage ~ sendRecipient => SendAction(sendRecipient, sendMessage.dropRight(3)) }

  def logMessage = ".*".r
  def logClause = "log" ~ logMessage ^^ { case _ ~ logMessage => LogAction(logMessage) }

  def minutes = ("[0-5]?[0-9]".r ~ ":") ^^ { case string ~ _ => Minutes(string.toInt) }
  def seconds = "[0-5][0-9]".r ^^ { string => Seconds(string.toInt) }
  def actionTimeExp = minutes ~ seconds ^^ { case minutes ~ seconds => minutes + seconds }
  def reactivateExp = "after" ~ (actionTimeExp | minutesMin | secondsSec) ^^ { case _ ~ time => time }
  def reactivateClause = "reactivate" ~ opt(reactivateExp) ^^ { case _ ~ reactivateDelay => ReactivateAction(reactivateDelay) }

  def deactivateClause = "deactivate" ^^ { _ => DeactivateAction() }

  def resetTarget = "Timer" | "Daylog"
  def resetClause = "reset" ~ resetTarget ^^ { case _  ~ resetTarget => ResetAction(resetTarget) }

  def sentenceStart = ("," ~ "then") | "and"
  def action = sentenceStart ~ (soundClause | warnClause | sendClause | logClause | reactivateClause | deactivateClause) ^^ { case _ ~ action => action.asInstanceOf[Action] }
}
