package de.htwg.signalk.html

import de.htwg.signalk.html.control.{ControlActions, ControlButtonsComponent}
import de.htwg.signalk.html.rule.RulesContainer
import de.htwg.signalk.html.selector.SelectorComponent
import org.scalajs.dom.document

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("RuleEditor")
object RuleEditor {
  val selector = new SelectorComponent
  val rules = new RulesContainer
  val controlActions = new ControlActions(rules, selector)
  val controlButtons = new ControlButtonsComponent(controlActions)

  @JSExport
  def render(): Unit = {
    if (document.getElementById("alarm-component") != null) {
      document.getElementById("alarm-component").appendChild(selector.html)
      document.getElementById("alarm-component").appendChild(controlButtons.html)
      document.getElementById("alarm-component").appendChild(rules.html)
    }
  }
}
