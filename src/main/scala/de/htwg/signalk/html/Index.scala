package de.htwg.signalk.html

import de.htwg.signalk.html.rule.Rule.buildRule
import de.htwg.signalk.html.rule.{DistanceExpression, TimeExpression, TimerExpression, ValueExpression}
import org.scalajs.dom.{Event, document}
import org.scalajs.dom.raw.HTMLSelectElement
import scalatags.JsDom.all._

object Index {
  def renderHtml(): Unit = {
    val rule = buildRule()

    val submitButton = span(button(
      "submit"
    )).render

    submitButton.addEventListener("click", { _: Event => {
      val expressionSelector = document.getElementById("expressionSelector").asInstanceOf[HTMLSelectElement].value

      expressionSelector match {
        case "value of" => println(ValueExpression.retrieveValueExpression())
        case "distance to" => println(DistanceExpression.retrieveValueExpression())
        case "time" => println(TimeExpression.retrieveTimeExpression())
        case "timer" => println(TimerExpression.retrieveTimerExpression())
      }
    }})
    
    document.body.appendChild(rule)
    document.body.appendChild(submitButton)
  }
}
