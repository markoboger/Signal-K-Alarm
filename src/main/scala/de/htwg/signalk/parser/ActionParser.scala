package de.htwg.signalk.parser

import squants.time.{Minutes, Seconds}

import scala.util.parsing.combinator.RegexParsers

class ActionParser extends RegexParsers {
  def sentenceStart = (", " ~ "then") | "and"

  def minute = "[0-5][0-9]".r ^^ { string => Minutes(string.toInt) }
  def seconds = "[0-5][0-9]".r ^^ { string => Seconds(string.toInt) }
  def soundType = "Alarm" | "Beep" | "Bell" | "Horn" | "Whistle"
  def amountOfTimes = "once" | "twice" | "[0-9]".r ~ "times"
  def maxMinutes = "max" ~ minute ~ "min" ^^ { case _ ~ minute ~ _ => minute}
  def maxSeconds = "max" ~ seconds ~ "sec" ^^ { case _ ~ seconds ~ _ => seconds}
  def maxTimes = maxMinutes | maxSeconds
  def soundExp = amountOfTimes | maxTimes | "until checked"
  def soundClause = "sound" ~ soundType ~ soundExp ^^ { case _ ~ soundType ~ soundExp => Action() }

}
