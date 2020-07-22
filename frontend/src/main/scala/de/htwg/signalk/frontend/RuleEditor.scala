package de.htwg.signalk.frontend

import de.htwg.signalk.frontend.control.{ControlActions, ControlButtonsComponent}
import de.htwg.signalk.frontend.rule.RulesContainer
import de.htwg.signalk.frontend.selector.SelectorComponent
import org.scalajs.dom.document

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel, ScalaJSDefined}
import com.karasiq.bootstrap4.Bootstrap.default._
import scalaTags.all._

@JSExportTopLevel("RuleEditor")
object RuleEditor {
  val selector = new SelectorComponent
  val rules = new RulesContainer
  val controlActions = new ControlActions(rules, selector)
  val controlButtons = new ControlButtonsComponent(controlActions)

  @JSExport
  def render(): Unit = {
    if (document.getElementById("alarm-component") != null) {

      val controlArea = GridSystem.row(
        GridSystem.col(12)(selector.render, textAlign.center),
        GridSystem.col(12)(controlButtons.render, textAlign.center),
        borderBottom := "3px solid"
      )

      document.getElementById("alarm-component").appendChild(controlArea.render)
      document.getElementById("alarm-component").appendChild(rules.render)
    }
  }
}
