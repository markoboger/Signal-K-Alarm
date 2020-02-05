package de.htwg.signalk

import org.querki.jquery._
import org.scalajs.dom
import scala.util.parsing.combinator._

object Alarm {

  val lengthTypes = List("Depth")
  val percentageTypes = List("Battery", "Fuel", "Performance")
  val degreeTypes = List("TWA", "TWD")
  val speedTypes = List("TWS", "AWS", "SOG", "STW", "VMG")
  val temperatureTypes = List("Air", "Water")
  val markerTypes = List("Waypoint", "Photo", "Log Entry", "Marker")
  val singleArgOps = List("below", "above")
  val twoArgOps = List("between", "outside")
  val arg1 = List("10", "20", "30")
  val arg2 = List("10", "20", "30")
  val actions = List("sound", "warn", "send", "log", "deactivate", "activate")
  val timerActions = List("reset", "restart")
  val sounds = List("Beep", "Alarm1", "Bell")
  val repeats = List("once", "twice", "3 times", "max 20 sec", "max 1 min", "max 3 min", "max 10 min", "until checked")


  def main(args: Array[String]): Unit = {
    $(dom.document).ready{ () => {
        selectionChanged
        $("select").change(() => {selectionChanged})
      }
    }

    def selectionChanged = {
      def showOneOf(selection: String, choices: List[String]): Unit ={
        for (choice <- choices if selection!=choice) {$(choice).hide}
        $(selection).show
      }

      def mapSelectionToChoice(selection:String, pairs:List[Tuple2[List[String],String]]): String = {
        for (pair <- pairs) {
          if (pair._1.contains(selection)) {
            return pair._2
          }
        }
        "No match"
      }

      def switchOnChoices(selectorId:String, selector:List[List[String]],choices:List[String]) = {
        val selection = $(selectorId+" :selected").valueString
        val pairs = selector zip choices
        val choice = mapSelectionToChoice(selection, pairs)
        showOneOf(choice,choices)
      }

      switchOnChoices("#ExpressionSelector",
        List(List("value of"), List("distance to"), List("time"), List("timer")),
        List("#ValueExpression","#MarkerExpression", "#TimeExpression", "#TimerExpression"))

      switchOnChoices("#ValueSelector",
        List(lengthTypes, percentageTypes, degreeTypes, speedTypes, temperatureTypes),
        List("#LengthExpression", "#PercentageExpression", "#DegreeExpression", "#SpeedExpression","#TemperatureExpression" ))

      switchOnChoices("#DepthOperatorSelector",
        List(singleArgOps,twoArgOps),
        List("#DepthSingleArgExpression", "#DepthTwoArgExpression"))

      switchOnChoices("#PercentageOperatorSelector",
        List(singleArgOps,twoArgOps),
        List("#PercentageSingleArgExpression", "#PercentageTwoArgExpression"))

      switchOnChoices("#DegreeOperatorSelector",
        List(singleArgOps,twoArgOps),
        List("#DegreeSingleArgExpression", "#DegreeTwoArgExpression"))

      switchOnChoices("#SpeedOperatorSelector",
        List(singleArgOps,twoArgOps),
        List("#SpeedSingleArgExpression", "#SpeedTwoArgExpression"))

      switchOnChoices("#TemperatureOperatorSelector",
        List(singleArgOps,twoArgOps),
        List("#TemperatureSingleArgExpression", "#TemperatureTwoArgExpression"))

      switchOnChoices("#DistanceOperatorSelector",
        List(singleArgOps,twoArgOps),
        List("#DistanceSingleArgExpression", "#DistanceTwoArgExpression"))

      switchOnChoices("#ActionSelector",
        List(List("sound"), List("warn"), List("log"), List("send"), List("deactivate"), List("reactivate"), List("reset"), List("restart")),
        List("#SoundAction", "#WarnAction", "#LogAction", "#SendAction", "#DeactivateAction", "#ReactivateAction", "#ResetAction", "#RestartAction"))
    }
    val sp = new RuleParser
    val result = sp.parseAll( sp.rule ,"When timer is -19:59")
    println(result.get)
  }
}
class RuleParser extends RegexParsers {
  def timeOperator ="timer" | "time"
  def sign = "-"
  def hour ="[0-1]*[0-9]".r | "2[0-3]".r
  def minute = "[0-5][0-9]".r
  def timeClause  = timeOperator~"is"~opt(sign)~hour~":"~minute
  def valueOperator="value"~"of" | "distance"~"to"
  def valueType = "Depth" | "TWA"
  def valueClause = valueOperator ~ valueType
  def rule = "When" ~ timeClause | valueClause ^^ { _.toString }
}
