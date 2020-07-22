package de.htwg.signalk.frontend.control

import de.htwg.signalk.frontend.rule.RulesContainer
import de.htwg.signalk.frontend.selector.SelectorComponent

class ControlActions(val retrieveRule: () => String,
                     val addAction: () => Unit,
                     val addEmptyRule: () => Unit,
                     val addRule: (String) => Unit,
                     val resetAction: () => Unit) {

  def this(rules: RulesContainer, selector: SelectorComponent) {
    this(() => selector.retrieveRule, () => selector.addAction,() => rules.addRule(), (String) => rules.addRule(String), () => selector.reset)
  }
}
