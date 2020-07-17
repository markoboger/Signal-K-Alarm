package de.htwg.signalk.html.control

import de.htwg.signalk.html.HTMLComponent
import de.htwg.signalk.html.rule.{RuleSentenceComponent, RulesContainer}
import org.scalajs.dom.raw.HTMLElement
import org.scalajs.dom.{Event, document}
import scalatags.JsDom.all._

class ControlButtonsComponent(retrieveRule: () => String, addAction: () => Unit, rulesContainer: RulesContainer) extends HTMLComponent {
  val newRuleButton = span(button("New Empty Rule")).render

  newRuleButton.addEventListener("click", { _: Event => {
    rulesContainer.addRule()
  }})

  val submitButton = span(button("Submit")).render

  submitButton.addEventListener("click", { _: Event => {
    rulesContainer.addRule(retrieveRule.apply())
  }})

  val newActionButton = span(button("Add Action")).render
  newActionButton.addEventListener("click", { _: Event => {
    addAction.apply()
  }})

  override val _id: String = "_gg"

  override def html: HTMLElement = div(submitButton, newActionButton, newRuleButton).render
}
