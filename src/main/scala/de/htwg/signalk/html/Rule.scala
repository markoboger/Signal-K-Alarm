package de.htwg.signalk.html

import de.htwg.signalk.html.Util.buildSelector
import de.htwg.signalk.html.TimeExpression.buildTimeExpression
import de.htwg.signalk.html.ValueExpression.buildValueExpression
import org.scalajs.dom.{Event, document}
import scalatags.JsDom.all._

object Rule {

  val expressionSelector = buildSelector(List("value of", "distance to", "time", "timer"))
  val valueExpression = buildValueExpression()
  val timeExpression = buildTimeExpression()

  def buildRule() = {
    val rule = div(
      id := "Rule",
      span(
        "When ",
        expressionSelector
      ),
      valueExpression
    ).render

    expressionSelector.addEventListener("change", { _: Event => {
      val activeExpression = document.getElementById("expression")

      expressionSelector.value match {
        case "value of" => rule.replaceChild(valueExpression, activeExpression)
        case _ => rule.replaceChild(timeExpression, activeExpression)
      }
    }})

    rule
  }
}
