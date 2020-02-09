package de.htwg.signalk

import scala.util.parsing.combinator.RegexParsers
import squants.{Dimensionless, Meters, Percent}
import squants.time.{Hours, Minutes, Time}
import squants.space.{Degrees, Feet, Kilometers, Length, NauticalMiles}
import squants.Angle
import squants.motion.{KilometersPerHour, Knots, MetersPerSecond, Velocity}
import squants.thermal.{Celsius, Fahrenheit, Temperature}

class RuleKParser extends RegexParsers {

  def sign = "[-]".r ^^ { _ => -1 }

  def hour01 = ("([0-1])?[0-9]".r ~ ":") ^^ { case string ~ _ => Hours(string.toInt) }

  def hour2 = ("2[0-3]".r ~ ":") ^^ { case string ~ _ => Hours(string.toInt) }

  def hour = hour01 | hour2

  def minute = "[0-5][0-9]".r ^^ { string => Minutes(string.toInt) }

  def timeExp = hour ~ minute ^^ { case hours ~ minutes => hours + minutes }

  def timeClause = "time" ~ "is" ~ timeExp ^^ { case t ~ _ ~ time => new Trigger[Time](t, TimeOperator[Time].is(time)(_)) }

  def timerClause = "timer" ~ "is" ~ opt(sign) ~ timeExp ^^ { case t ~ _ ~ sign ~ time => new Trigger[Time](t, TimeOperator[Time].is(sign.getOrElse(1) * time)(_)) }

  def valueHole = "[0-9]{1,5}".r ^^ { case numString => numString.toInt }

  def valueFracture = "." ~ "[0-9]{1,3}".r ^^ { case "." ~ fract => fract.toDouble / scala.math.pow(10, fract.length) }

  def value = valueHole ~ opt(valueFracture) ^^ { case hole ~ fract => hole.toDouble + fract.getOrElse(0.0) }

  def oneArgOp = "below" | "above"

  def twoArgOp = "between" | "outside"

  def unitValue3 = value ~ ("m" | "ft" | "km" | "nm") ^^ {
    case value ~ "m" => Meters(value)
    case value ~ "ft" => Feet(value)
    case value ~ "km" => Kilometers(value)
    case value ~ "nm" => NauticalMiles(value)
  }

  def unitValue = new LengthParser().unitValue.asInstanceOf[Parser[Length]]

  def signedValue = opt(sign) ~ value ^^ { case sign ~ value => sign.getOrElse(1) * value }

  def oneArgExp = oneArgOp ~ unitValue ^^ {
    case "below" ~ value => OrderOperator[Length].below(value)(_)
    case "above" ~ value => OrderOperator[Length].above(value)(_)
  }

  def twoArgExp = twoArgOp ~ unitValue ~ "and" ~ unitValue ^^ {
    case "between" ~ valueA ~ "and" ~ valueB if valueA < valueB => OrderOperator[Length].between(valueA, valueB)(_)
    case "outside" ~ valueA ~ "and" ~ valueB if valueA < valueB => OrderOperator[Length].outside(valueA, valueB)(_)
  }

  def argExp = oneArgExp | twoArgExp

  def depthClause = "value" ~ "of" ~ "Depth" ~ "is" ~ argExp ^^ { case _ ~ _ ~ depth ~ _ ~ depthExp => new DepthTrigger(depth, depthExp) }

  /*
  def speed3 = value ~ ("kn" | "m/s" | "km/h") ^^ {
    case value ~ "kn" => Knots(value)
    case value ~ "m/s" => MetersPerSecond(value)
    case value ~ "km/h" => KilometersPerHour(value)
  }

 def oneArgSpeedExp = oneArgOp ~ speed ^^ {
    case "below" ~ speed => OrderOperator[Velocity].below(speed)(_)
    case "above" ~ speed => OrderOperator[Velocity].above(speed)(_)
  }

  def twoArgSpeedExp = twoArgOp ~ speed ~ "and" ~ speed ^^ {
    case "between" ~ speedA ~ "and" ~ speedB => OrderOperator[Velocity].between(speedA, speedB)(_)
    case "outside" ~ speedA ~ "and" ~ speedB => OrderOperator[Velocity].outside(speedA, speedB)(_)
  }*/
  val speedParser = new SpeedParser()
  def speed = speedParser.unitValue.asInstanceOf[Parser[Velocity]]

  def speedExp = speedParser.argExp.asInstanceOf[Parser[Velocity => Boolean]] //oneArgSpeedExp | twoArgSpeedExp

  def speedType = "SOG" | "STW" | "AWS" | "TWS" | "VMG"

  def speedClause = "value" ~ "of" ~ speedType ~ "is" ~ speedExp ^^ { case _ ~ _ ~ speedType ~ _ ~ speedExp => new Trigger[Velocity](speedType, speedExp) }

  def distanceExp = oneArgExp | twoArgExp

  def distanceType = "Marker" | "LogEntry" | "Photo" | "Waypoint"

  def distanceClause = "distance" ~ "to" ~ distanceType ~ "is " ~ distanceExp ^^ { case _ ~ _ ~ distanceType ~ _ ~ distance => new Trigger[Length](distanceType, distance) }

  val angleParser = new AngleParser()
  def angle = angleParser.unitValue.asInstanceOf[Parser[Angle]]//signedValue ~ ("°") ^^ { case value ~ "°" => Degrees(value) }

/*  def oneArgAngleExp = oneArgOp ~ angle ^^ {
    case "below" ~ angle => OrderOperator[Angle].below(angle)(_)
    case "above" ~ angle => OrderOperator[Angle].above(angle)(_)
  }

  def twoArgAngleExp = twoArgOp ~ angle ~ "and" ~ angle ^^ {
    case "between" ~ angleA ~ "and" ~ angleB => OrderOperator[Angle].between(angleA, angleB)(_)
    case "outside" ~ angleA ~ "and" ~ angleB => OrderOperator[Angle].outside(angleA, angleB)(_)
  }
*/
  def angleExp = angleParser.argExp.asInstanceOf[Parser[Angle=>Boolean]]//oneArgAngleExp | twoArgAngleExp

  def angleType = "TWA" | "TWD" | "AWA" | "AWD" | "HDG" | "COG" | "CTW"

  def angleClause = "value" ~ "of" ~ angleType ~ "is" ~ angleExp ^^ { case _ ~ _ ~ angleType ~ _ ~ angleExp => new Trigger[Angle](angleType, angleExp) }

  def percent = value ~ ("%") ^^ { case value ~ _ => Percent(value) }

  def oneArgPercentExp = oneArgOp ~ percent ^^ {
    case "below" ~ percent => OrderOperator[Dimensionless].below(percent)(_)
    case "above" ~ percent => OrderOperator[Dimensionless].above(percent)(_)
  }

  def twoArgPercentExp = twoArgOp ~ percent ~ "and" ~ percent ^^ {
    case "between" ~ percentA ~ "and" ~ percentB => OrderOperator[Dimensionless].between(percentA, percentB)(_)
    case "outside" ~ percentA ~ "and" ~ percentB => OrderOperator[Dimensionless].outside(percentA, percentB)(_)
  }

  def percentExp = oneArgPercentExp | twoArgPercentExp

  def percentType = "Battery" | "Fuel" | "Performance"

  def percentClause = "value" ~ "of" ~ percentType ~ "is" ~ percentExp ^^ { case _ ~ _ ~ percentType ~ _ ~ percentExp => new Trigger[Dimensionless](percentType, percentExp) }

  def temp = signedValue ~ ("°C" | "°F") ^^ {
    case value ~ "°C" => Celsius(value)
    case value ~ "°F" => Fahrenheit(value)
  }

  def oneArgTempExp = oneArgOp ~ temp ^^ {
    case "below" ~ temp => OrderOperator[Temperature].below(temp)(_)
    case "above" ~ temp => OrderOperator[Temperature].above(temp)(_)
  }

  def twoArgTempExp = twoArgOp ~ temp ~ "and" ~ temp ^^ {
    case "between" ~ tempA ~ "and" ~ tempB => OrderOperator[Temperature].between(tempA, tempB)(_)
    case "outside" ~ tempA ~ "and" ~ tempB => OrderOperator[Temperature].outside(tempA, tempB)(_)
  }

  def tempExp = oneArgTempExp | twoArgTempExp

  def tempType = "Air" | "Water" | "Engine"

  def tempClause = "value" ~ "of" ~ tempType ~ "is" ~ tempExp ^^ { case _ ~ _ ~ tempType ~ _ ~ tempExp => new Trigger[Temperature](tempType, tempExp) }


  def lengthTrigger = "When" ~ (distanceClause | depthClause) ^^ { case _ ~ trigger => Rule(trigger, new Action) }

  def timeTrigger = "When" ~ (timeClause | timerClause) ^^ { case _ ~ trigger => Rule(trigger, new Action) }

  def angleTrigger = "When" ~ angleClause ^^ { case _ ~ trigger => Rule(trigger, new Action) }

  def trigger = lengthTrigger | timeTrigger | angleTrigger

  abstract class OperatorParser[A<:Ordered[A]] extends RegexParsers {
    def valueHole = "[0-9]{1,5}".r ^^ { case numString => numString.toInt }

    def valueFracture = "." ~ "[0-9]{1,3}".r ^^ { case "." ~ fract => fract.toDouble / scala.math.pow(10, fract.length) }

    def value = valueHole ~ opt(valueFracture) ^^ { case hole ~ fract => hole.toDouble + fract.getOrElse(0.0) }

    def sign = "[-]".r ^^ { _ => -1 }

    def signedValue = opt(sign) ~ value ^^ { case sign ~ value => sign.getOrElse(1) * value }

    def oneArgOp = "below" | "above"

    def twoArgOp = "between" | "outside"

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

  class LengthParser extends OperatorParser[Length] {
    def unitValue = value ~ ("m" | "ft" | "km" | "nm") ^^ {
      case value ~ "m" => Meters(value)
      case value ~ "ft" => Feet(value)
      case value ~ "km" => Kilometers(value)
      case value ~ "nm" => NauticalMiles(value)
    }
  }

  class SpeedParser extends OperatorParser[Velocity] {
    def unitValue = value ~ ("kn" | "m/s" | "km/h") ^^ {
      case value ~ "kn" => Knots(value)
      case value ~ "m/s" => MetersPerSecond(value)
      case value ~ "km/h" => KilometersPerHour(value)
    }
  }
  class AngleParser extends OperatorParser[Angle] {
    def unitValue = signedValue ~ ("°") ^^ {
      case value ~ "°" => Degrees(value)
    }
  }

}