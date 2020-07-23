package de.htwg.signalk.frontend

import de.htwg.signalk.frontend.BootStrapComponents.GridLayout
import de.htwg.signalk.frontend.control.{ControlActions, ControlButtonsComponent}
import de.htwg.signalk.frontend.rule.RulesContainer
import de.htwg.signalk.frontend.selector.SelectorComponent
import org.scalajs.dom.document

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import org.scalajs.dom.raw.HTMLElement
import scalatags.JsDom.all._

@JSExportTopLevel("RuleEditor")
object RuleEditor {
  val rules = new RulesContainer
  val selector = new SelectorComponent
  val controlActions = new ControlActions(rules, selector)
  val controlButtons = new ControlButtonsComponent(controlActions)

  def controlArea = GridLayout.row(12, borderBottom:="3px solid", id:="control-area",
    GridLayout.col(12, textAlign:="center", selector.render),
    GridLayout.col(12, textAlign:="center", controlButtons.render)
  )

  @JSExport
  def render(): Unit = {
    if (document.getElementById("alarm-component") != null) {
      document.getElementById("alarm-component").appendChild(controlArea.render)
      document.getElementById("alarm-component").appendChild(rules.render)
    }
  }

  def update(callback: () => Unit): Unit = {
    callback()
    val oldControlArea = document.getElementById("control-area").asInstanceOf[HTMLElement]
    val oldRules = document.getElementById("rules").asInstanceOf[HTMLElement]
    oldControlArea.parentElement.replaceChild(controlArea.render, oldControlArea)
    oldRules.parentElement.replaceChild(rules.render, oldRules)
  }
}