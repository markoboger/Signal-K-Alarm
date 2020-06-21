package de.htwg.signalk.html.rule

import de.htwg.signalk.html.Util.{buildSelector, createOnChoiceSwitch}
import de.htwg.signalk.html.rule.TimeExpression.timeExpression
import de.htwg.signalk.html.rule.TimerExpression.timerExpression
import de.htwg.signalk.html.rule.ValueExpression.valueExpression
import de.htwg.signalk.html.rule.DistanceExpression.distanceExpression
import scalatags.JsDom.all._

object Rule {

  def expressionSelector = buildSelector(List("value of", "distance to", "time", "timer"), "expressionSelector")

  def rule = {
    val expressionSelector = this.expressionSelector

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
