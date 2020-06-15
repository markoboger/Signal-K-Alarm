package de.htwg.signalk.html.rule

import de.htwg.signalk.html.Util.{buildSelector, createOnChoiceSwitch}
import de.htwg.signalk.html.rule.TimeExpression.buildTimeExpression
import de.htwg.signalk.html.rule.TimerExpression.buildTimerExpression
import de.htwg.signalk.html.rule.ValueExpression.buildValueExpression
import de.htwg.signalk.html.rule.DistanceExpression.buildDistanceExpression
import scalatags.JsDom.all._

object Rule {

  val expressionSelector = buildSelector(List("value of", "distance to", "time", "timer"), "expressionSelector")
  val valueExpression = buildValueExpression()
  val distanceExpression = buildDistanceExpression()
  val timeExpression = buildTimeExpression()
  val timerExpression = buildTimerExpression()

  def buildRule() = {
    val rule = div(
      id := "Rule",
      span(
        "When ",
        expressionSelector
      ),
      valueExpression
    ).render

    createOnChoiceSwitch(expressionSelector, "rule-expression", rule,
      List("value of", "distance to", "time", "timer"),
      List(valueExpression, distanceExpression, timeExpression, timerExpression)
    )

    rule
  }
}
