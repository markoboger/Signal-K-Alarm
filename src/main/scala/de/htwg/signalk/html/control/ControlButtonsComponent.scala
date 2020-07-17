package de.htwg.signalk.html.control

import de.htwg.signalk.html.HTMLComponent
import de.htwg.signalk.html.rule.{RuleSentenceComponent, RulesContainer}
import org.scalajs.dom.raw.HTMLElement
import org.scalajs.dom.{Event, document}
import scalatags.JsDom.all._

class ControlButtonsComponent(val controlActions: ControlActions) extends HTMLComponent {
  val newRuleButton = span(button("New Empty Rule")).render
  newRuleButton.addEventListener("click", { _: Event => {
    controlActions.addEmptyRule.apply()
  }})

  val submitButton = span(button("Submit")).render
  submitButton.addEventListener("click", { _: Event => {
    controlActions.addRule(controlActions.retrieveRule.apply())
  }})

  val newActionButton = span(button("Add Action")).render
  newActionButton.addEventListener("click", { _: Event => {
    controlActions.addAction.apply()
  }})

  val resetEditorButton = span(button("Reset Editor")).render
  resetEditorButton.addEventListener("click", { _: Event => {
    controlActions.resetAction.apply()
  }})

  override val _id: String = "_gg"

  override def html: HTMLElement = div(submitButton, newActionButton, newRuleButton, resetEditorButton).render
}
