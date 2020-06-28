package de.htwg.signalk.html

import de.htwg.signalk.html.rule.Rule
import de.htwg.signalk.html.action.{Action, SoundExpression}
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
    document.body.appendChild(newRuleButton)
  }

  def newRuleButton = {
    val newRuleButton = span(button(
      "Add Rule"
    )).render

    newRuleButton.addEventListener("click", { _: Event => {
      document.body.appendChild(Rule.createRule(""))
    }})

    newRuleButton
  }

  def submitButton = {
    val submitButton = span(button(
      "Submit"
    )).render

    submitButton.addEventListener("click", { _: Event => {
      val expressionSelector = document.getElementById("expressionSelector").asInstanceOf[HTMLSelectElement].value
      val actionSelector = document.getElementById("actionSelector").asInstanceOf[HTMLSelectElement].value

      val trigger = expressionSelector match {
        case "value of" => ValueExpression.retrieveValueExpression()
        case "distance to" => DistanceExpression.retrieveDistanceExpression()
        case "time" => TimeExpression.retrieveTimeExpression()
        case "timer" => TimerExpression.retrieveTimerExpression()
      }

      val action = actionSelector match {
        case "sound" => SoundExpression.retrieveSoundExpression()
//        case "warn" => println(DistanceExpression.retrieveDistanceExpression())
//        case "log" => println(TimeExpression.retrieveTimeExpression())
//        case "send" => println(TimerExpression.retrieveTimerExpression())
//        case "activate" => println(TimerExpression.retrieveTimerExpression())
//        case "deactivate" => println(TimerExpression.retrieveTimerExpression())
//        case "reset" => println(TimerExpression.retrieveTimerExpression())
//        case "restart" => println(TimerExpression.retrieveTimerExpression())
      }

      document.body.appendChild(Rule.createRule(trigger + action))

    }})

    submitButton
  }
}
