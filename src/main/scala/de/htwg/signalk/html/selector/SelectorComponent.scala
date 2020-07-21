package de.htwg.signalk.html.selector

import de.htwg.signalk.html.HTMLComponent
import de.htwg.signalk.html.selector.action.ActionsContainer
import de.htwg.signalk.html.selector.trigger.TriggerComponent
import org.scalajs.dom.raw.HTMLElement
import scalatags.JsDom.all._

class SelectorComponent extends HTMLComponent {
  override val _id: String = "selector"

  private var trigger = new TriggerComponent(this)
  private var action = new ActionsContainer

  override def render: HTMLElement = {
    div(
      id:=_id,
      trigger.render,
      action.render
    ).render
  }

  def retrieveRule = trigger.retrieveTrigger + action.retrieveAction
  def addAction = action.addAction
  def reset = {
    update(() => {
      trigger = new TriggerComponent(this)
      action = new ActionsContainer
    })
  }
}
