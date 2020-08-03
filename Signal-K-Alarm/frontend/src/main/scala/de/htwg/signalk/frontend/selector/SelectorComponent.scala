package de.htwg.signalk.frontend.selector

import de.htwg.signalk.frontend.{HTMLComponent, RuleEditor}
import de.htwg.signalk.frontend.selector.action.ActionsContainer
import de.htwg.signalk.frontend.selector.trigger.TriggerComponent
import org.scalajs.dom.raw.HTMLElement
import scalatags.JsDom.all._

class SelectorComponent extends HTMLComponent {
  private var trigger = new TriggerComponent
  private var action = new ActionsContainer

  override def render: HTMLElement = div(trigger.render, action.render).render

  def retrieveRule = trigger.retrieveTrigger + action.retrieveAction
  def addAction = action.addAction
  def reset = RuleEditor.update(() => {
    trigger = new TriggerComponent
    action = new ActionsContainer
  })
}
