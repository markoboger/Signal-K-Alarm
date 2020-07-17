package de.htwg.signalk.html.control

import de.htwg.signalk.html.rule.RulesContainer
import de.htwg.signalk.html.selector.SelectorComponent
import de.htwg.signalk.html.selector.action.ActionsContainer

class ControlActions(val retrieveRule: () => String,
                     val addAction: () => Unit,
                     val addEmptyRule: () => Unit,
                     val addRule: (String) => Unit,
                     val resetAction: () => Unit) {

  def this(rules: RulesContainer, selector: SelectorComponent) {
    this(() => selector.retrieveRule, () => selector.addAction,() => rules.addRule(), (String) => rules.addRule(String), () => selector.reset)
  }
}
