package de.htwg.signalk.html.action

import de.htwg.signalk.html.Util.buildSelector
import de.htwg.signalk.html.action.SoundExpression.buildSoundExpression
import de.htwg.signalk.html.rule.Rule.expressionSelector
import org.scalajs.dom.{Event, document}
import scalatags.JsDom.all._

object Action {
  val actionSelector = buildSelector(List("sound", "warn", "log", "send", "activate", "deactivate", "reset", "restart"), "actionSelector")

  val soundExpression = buildSoundExpression()

  def buildAction(): Unit = {
    val action = div(
      id := "Rule",
      span(
        "When ",
        actionSelector
      ),
      soundExpression
    ).render

    expressionSelector.addEventListener("change", { _: Event => {
      val activeExpression = document.getElementById("action-expression")

      expressionSelector.value match {
        case "sound" => action.replaceChild(soundExpression, activeExpression)
        case "" => action.removeChild(activeExpression)
        case _ => action.replaceChild(soundExpression, activeExpression)
      }
    }})

  }

}
