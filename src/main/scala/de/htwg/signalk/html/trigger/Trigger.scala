package de.htwg.signalk.html.trigger

import de.htwg.signalk.html.Util.{buildSelector, createOnChoiceSwitch}
import de.htwg.signalk.html.trigger.TimeExpression.timeExpression
import de.htwg.signalk.html.trigger.TimerExpression.timerExpression
import de.htwg.signalk.html.trigger.ValueExpression.valueExpression
import de.htwg.signalk.html.trigger.DistanceExpression.distanceExpression
import scalatags.JsDom.all._

object Trigger {

  def expressionSelector = buildSelector(List("value of", "distance to", "time", "timer"), "expressionSelector")

  def trigger = {
    val expressionSelector = this.expressionSelector

    val trigger = div(
      id := "trigger",
      span(
        "When ",
        expressionSelector
      ),
      valueExpression
    ).render

    createOnChoiceSwitch(expressionSelector, "trigger-expression", trigger,
      List("value of", "distance to", "time", "timer"),
      List(valueExpression, distanceExpression, timeExpression, timerExpression)
    )

    trigger
  }
}
