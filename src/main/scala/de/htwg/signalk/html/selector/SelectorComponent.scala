package de.htwg.signalk.html.selector

import de.htwg.signalk.html.HTMLComponent
import de.htwg.signalk.html.selector.action.ActionsContainer
import de.htwg.signalk.html.selector.trigger.TriggerComponent
import org.scalajs.dom.raw.HTMLElement
import scalatags.JsDom.all._

class SelectorComponent extends HTMLComponent {
  override val _id: String = "selector"

  private var trigger = new TriggerComponent
  private var action = new ActionsContainer

  override def html: HTMLElement = {
    div(
      id:=_id,
      trigger.html,
      action.html
    ).render
  }

  def retrieveRule = trigger.retrieveTrigger + action.retrieveAction
  def addAction = action.addAction
  def reset = {
    update(() => {
      trigger = new TriggerComponent
      action = new ActionsContainer
    })
  }
}
