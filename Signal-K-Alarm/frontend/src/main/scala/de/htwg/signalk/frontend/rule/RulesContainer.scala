package de.htwg.signalk.frontend.rule

import de.htwg.signalk.frontend.{HTMLComponent, RuleEditor}
import org.scalajs.dom.raw.HTMLElement
import scalatags.JsDom.all._

class RulesContainer extends HTMLComponent {
  var rules: List[RuleComponent] = Nil

  override def render: HTMLElement = {
    div(
      id:="rules",
      paddingTop:="10px",
      for(rule <- rules) yield rule.render
    ).render
  }

  def addRule(ruleText: String) = RuleEditor.update(() => rules = new RuleComponent(ruleText, this)::rules)
  def addRule() = RuleEditor.update(() => rules = new RuleComponent("", this)::rules)
  def removeRule(rule: RuleComponent) = RuleEditor.update(() => rules = rules.filterNot(elm => elm == rule))
}
