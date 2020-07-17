package de.htwg.signalk.html.rule

import de.htwg.signalk.html.HTMLComponent
import org.scalajs.dom.raw.HTMLElement
import scalatags.JsDom.all._

class RulesContainer extends HTMLComponent {
  override val _id: String = "rules-container"

  var rules: List[RuleSentenceComponent] = Nil

  override def html: HTMLElement = {
    div(
      id:=_id,
      for(rule <- rules) yield rule.html
    ).render
  }

  def addRule(ruleText: String) = update(() => rules = new RuleSentenceComponent(ruleText, this)::rules)
  def addRule() = update(() => rules = new RuleSentenceComponent("", this)::rules)
  def removeRule(rule: RuleSentenceComponent) = update(() => rules = rules.filterNot(elm => elm == rule))
}
