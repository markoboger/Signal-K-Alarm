package de.htwg.signalk.html.action

import de.htwg.signalk.html.Util.buildSelectorWithID
import de.htwg.signalk.html.action.SoundExpression.buildSoundExpression
import org.scalajs.dom.{Event, document}
import scalatags.JsDom.all._
import org.scalajs.dom.html.Div

object Action {
  def actionSelector = buildSelectorWithID(List("sound", "warn", "log", "send", "activate", "deactivate", "reset", "restart"), "actionSelector")
  def soundExpression = buildSoundExpression()

  def action = {
    val action = div(
      id := "Action",
      span(
        ", then ",
        actionSelector
      ),
      buildSoundExpression(),
      addButton
    ).render

    actSelectionChange(action)
    action
  }

  private def actSelectionChange(action: Div)= {
    actionSelector.addEventListener("change", { _: Event => {
      val activeExpression = document.getElementById("action-expression")

      actionSelector.value match {
        case "sound" => action.replaceChild(soundExpression, activeExpression)
        case "" => action.removeChild(activeExpression)
        case _ => action.replaceChild(soundExpression, activeExpression)
      }
    }})
  }

  private def addButton = {
    val addButton = span(
      button("+")
    ).render

    addButton.addEventListener("click", { _: Event => {
      addAction()
    }})

    addButton
  }

  private def removeButton = {
    span(button(
      "-"
    )).render
  }

  private def addAction(): Unit = {
    val actionElement = document.getElementById("Action")
    val removeButton = this.removeButton

    val additionalAction = div(
      span(
        ", and ",
        actionSelector
      ),
      soundExpression,
      span(
        removeButton
      )
    ).render

    additionalAction.classList.add("additional-action")

    actionElement.appendChild(additionalAction)

    removeButton.addEventListener("click", { _: Event => {
      actionElement.removeChild(additionalAction)
    }})
  }
}
