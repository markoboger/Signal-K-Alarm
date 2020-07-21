package de.htwg.signalk.html.rule

import de.htwg.signalk.html.HTMLComponent
import org.scalajs.dom.raw.HTMLElement
import com.karasiq.bootstrap4.Bootstrap.default._
import scalaTags.all._

class RulesContainer extends HTMLComponent {
  override val _id: String = "rules-container"

  var rules: List[RuleComponent] = Nil

  override def render: HTMLElement = {
    div(
      id:=_id,
      paddingTop:="10px",
      for(rule <- rules) yield rule.render
    ).render
  }

  def addRule(ruleText: String) = update(() => rules = new RuleComponent(ruleText, this)::rules)
  def addRule() = update(() => rules = new RuleComponent("", this)::rules)
  def removeRule(rule: RuleComponent) = update(() => rules = rules.filterNot(elm => elm == rule))
}
