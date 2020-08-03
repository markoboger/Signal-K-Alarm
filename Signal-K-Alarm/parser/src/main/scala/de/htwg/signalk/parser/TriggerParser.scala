package de.htwg.signalk.parser

import de.htwg.signalk.parser.RuleConstants._
import squants.motion.{KilometersPerHour, Knots, MetersPerSecond, Velocity}
import squants.space._
import squants.thermal.{Celsius, Fahrenheit, Temperature}
import squants.time.{Hours, Minutes, Time}
import squants.{Angle, Dimensionless, Meters, Percent}

trait TriggerParser extends CustomParser {
  def sign = "[-]".r ^^ { _ => -1 }
  def hour01 = ("([0-1])?[0-9]".r ~ ":") ^^ { case string ~ _ => Hours(string.toInt) }
  def hour2 = ("2[0-3]".r ~ ":") ^^ { case string ~ _ => Hours(string.toInt) }
  def hour = hour01 | hour2
  def minute = "[0-5][0-9]".r ^^ { string => Minutes(string.toInt) }
  def timeExp = hour ~ minute ^^ { case hours ~ minutes => hours + minutes }
  def timeCondition = "time" ~ "is" ~ timeExp ^^ { case t ~ _ ~ time => new Trigger[Time](t, TimeOperator[Time].is(time)(_)) }

  def timerCondition = "timer" ~ "is" ~ opt(sign) ~ timeExp ^^ { case t ~ _ ~ sign ~ time => new Trigger[Time](t, TimeOperator[Time].is(sign.getOrElse(1) * time)(_)) }

  def depthExp = DepthParser().argExp.asInstanceOf[Parser[Length => Boolean]]
  def depthCondition = "value" ~ "of" ~ "Depth" ~ "is" ~ depthExp ^^ { case _ ~ _ ~ depth ~ _ ~ depthExp => new Trigger[Length](depth, depthExp) }

  def distanceExp = DistanceParser().argExp.asInstanceOf[Parser[Length => Boolean]]
  def distanceType = orExpressionFromList(DistanceType)
  def distanceCondition = "distance" ~ "to" ~ distanceType ~ "is " ~ distanceExp ^^ { case _ ~ _ ~ distanceType ~ _ ~ distance => new Trigger[Length](distanceType, distance) }

  def speedExp = SpeedParser().argExp.asInstanceOf[Parser[Velocity => Boolean]]
  def speedType = orExpressionFromList(SpeedType)
  def speedCondition = "value" ~ "of" ~ speedType ~ "is" ~ speedExp ^^ { case _ ~ _ ~ speedType ~ _ ~ speedExp => new Trigger[Velocity](speedType, speedExp) }

  def angleExp = AngleParser().argExp.asInstanceOf[Parser[Angle=>Boolean]]
  def angleType = orExpressionFromList(AngleType)
  def angleCondition = "value" ~ "of" ~ angleType ~ "is" ~ angleExp ^^ { case _ ~ _ ~ angleType ~ _ ~ angleExp => new Trigger[Angle](angleType, angleExp) }

  def percentExp = PercentParser().argExp.asInstanceOf[Parser[Dimensionless => Boolean]]
  def percentType = orExpressionFromList(PercentType)
  def percentCondition = "value" ~ "of" ~ percentType ~ "is" ~ percentExp ^^ { case _ ~ _ ~ percentType ~ _ ~ percentExp => new Trigger[Dimensionless](percentType, percentExp) }

  def tempExp =  TempParser().argExp.asInstanceOf[Parser[Temperature=>Boolean]]
  def tempType = orExpressionFromList(TempType)
  def tempCondition = "value" ~ "of" ~ tempType ~ "is" ~ tempExp ^^ { case _ ~ _ ~ tempType ~ _ ~ tempExp => new Trigger[Temperature](tempType, tempExp) }

  def trigger = "When" ~ (timeCondition | timerCondition | depthCondition | distanceCondition | speedCondition | angleCondition | percentCondition | tempCondition) ^^ { case _ ~ trigger => trigger }

  abstract class OperatorParser[A<:Ordered[A]] extends CustomParser {
    def valueHole = "[0-9]{1,5}".r ^^ { case numString => numString.toInt }
    def valueFracture = "" ~ "[0-9]{1,3}".r ^^ { case "" ~ fract => fract.toDouble / scala.math.pow(10, fract.length) }
    def value = valueHole ~ opt(valueFracture) ^^ { case hole ~ fract => hole.toDouble + fract.getOrElse(0.0) }
    def sign = "[-]".r ^^ { _ => -1 }
    def signedValue = opt(sign) ~ value ^^ { case sign ~ value => sign.getOrElse(1) * value }
    def oneArgOp = orExpressionFromList(OneArgOp)
    def twoArgOp = orExpressionFromList(TwoArgOp)
    def unitValue:Parser[A]
    def oneArgExp = oneArgOp ~ unitValue ^^ {
      case "below" ~ value => OrderOperator[A].below(value)(_)
      case "above" ~ value => OrderOperator[A].above(value)(_)
    }
    def twoArgExp = twoArgOp ~ unitValue ~ "and" ~ unitValue ^^ {
      case "between" ~ valueA ~ "and" ~ valueB if valueA < valueB => OrderOperator[A].between(valueA, valueB)(_)
      case "outside" ~ valueA ~ "and" ~ valueB if valueA < valueB => OrderOperator[A].outside(valueA, valueB)(_)
    }
    def argExp = oneArgExp | twoArgExp
  }
  case class DepthParser() extends OperatorParser[Length] {
    def unitValue = value ~ ("m" | "ft") ^^ {
      case value ~ "m" => Meters(value)
      case value ~ "ft" => Feet(value)
    }
  }
  case class DistanceParser() extends OperatorParser[Length] {
    def unitValue = value ~ ("m" | "ft" | "km" | "nm") ^^ {
      case value ~ "m" => Meters(value)
      case value ~ "ft" => Feet(value)
      case value ~ "km" => Kilometers(value)
      case value ~ "nm" => NauticalMiles(value)
    }
  }
  case class SpeedParser() extends OperatorParser[Velocity] {
    def unitValue = value ~ ("kn" | "m/s" | "km/h") ^^ {
      case value ~ "kn" => Knots(value)
      case value ~ "m/s" => MetersPerSecond(value)
      case value ~ "km/h" => KilometersPerHour(value)
    }
  }
  case class AngleParser() extends OperatorParser[Angle] {
    def unitValue = signedValue ~ ("°") ^^ {
      case value ~ "°" => Degrees(value)
    }
  }
  case class PercentParser() extends OperatorParser[Dimensionless] {
    def unitValue = value ~ ("%") ^^ {
      case value ~ _ => Percent(value)
    }
  }
  case class TempParser() extends OperatorParser[Temperature] {
    def unitValue = signedValue ~ ("°C" | "°F") ^^ {
      case value ~ "°C" => Celsius(value)
      case value ~ "°F" => Fahrenheit(value)
    }
  }
}