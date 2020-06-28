package de.htwg.signalk.html

import de.htwg.signalk.html.action.Action
import de.htwg.signalk.html.trigger.Trigger
import de.htwg.signalk.html.trigger.{DistanceExpression, TimeExpression, TimerExpression, ValueExpression}
import org.scalajs.dom.raw.HTMLSelectElement
import org.scalajs.dom.{Event, document}
import scalatags.JsDom.all._

object Index {
  def renderHtml(): Unit = {
    document.body.appendChild(Trigger.trigger)
    document.body.appendChild(Action.action)
    document.body.appendChild(submitButton)
  }

  def submitButton = {
    val submitButton = span(button(
      "submit"
    )).render

    submitButton.addEventListener("click", { _: Event => {
      val expressionSelector = document.getElementById("expressionSelector").asInstanceOf[HTMLSelectElement].value

      expressionSelector match {
        case "value of" => println(ValueExpression.retrieveValueExpression())
        case "distance to" => println(DistanceExpression.retrieveDistanceExpression())
        case "time" => println(TimeExpression.retrieveTimeExpression())
        case "timer" => println(TimerExpression.retrieveTimerExpression())
      }
    }})

    submitButton
  }
}
