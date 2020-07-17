package de.htwg.signalk.html

import de.htwg.signalk.html.control.ControlButtonsComponent
import de.htwg.signalk.html.rule.RulesContainer
import de.htwg.signalk.html.selector.SelectorComponent
import org.scalajs.dom.document

class Index {
  val selector = new SelectorComponent
  val rules = new RulesContainer
  val buttons = new ControlButtonsComponent(() => selector.retrieveRule, () => selector.addAction, rules)

  def html = {
    document.getElementById("alarm-component").appendChild(selector.html)
    document.getElementById("alarm-component").appendChild(buttons.html)
    document.getElementById("alarm-component").appendChild(rules.html)
  }
}

