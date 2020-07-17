package de.htwg.signalk.html.selector.action

import de.htwg.signalk.html.Util.buildSelectorWithID
import de.htwg.signalk.html.HTMLComponent
import org.scalajs.dom.Event
import org.scalajs.dom.raw.HTMLElement
import scalatags.JsDom.all._

class ActionComponent(val num: Int, val actionContainer: ActionsContainer) extends HTMLComponent {
  override val _id: String = "action-" + num

  val selector = buildSelectorWithID(List("sound", "warn", "log", "send", "activate", "deactivate", "reset", "restart"), "action-selector")
  var clause: ActionClause = new SoundClause(num)

  selector.addEventListener("change", { _: Event => {
    update(() => selector.value match {
      case "sound" => clause = new SoundClause(num)
      case "warn" => clause = new WarnClause(num)
      case "log" => clause = new LogClause(num)
      case "send" => clause = new SendClause(num)
      case "activate" => clause = new ActivateClause(num)
      case "deactivate" => clause = new DeactivateClause(num)
      case "reset" => clause = new ResetClause(num)
      case "restart" => clause = new RestartClause(num)
    })
  }})

  override def html: HTMLElement = {
    div(
      id := _id,
      div(span(if(num == 0) {", then "} else { ", and " }, selector), clause.html, removeButton),
    ).render
  }

  val removeButton = {
    span(button(
      "-"
    )).render
  }

  removeButton.addEventListener("click", { _: Event => {
    removeAction
  }})

  def retrieveAction = clause.retrieveAction
  def removeAction = actionContainer.removeAction(this)
}
