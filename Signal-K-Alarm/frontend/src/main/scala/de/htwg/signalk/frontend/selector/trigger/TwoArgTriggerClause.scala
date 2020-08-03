package de.htwg.signalk.frontend.selector.trigger

import de.htwg.signalk.frontend.BootStrapComponents.{BootStrapInput, BootStrapSelector}
import de.htwg.signalk.parser.RuleConstants._
import org.scalajs.dom.Element
import org.scalajs.dom.raw.HTMLSelectElement
import scalatags.JsDom.all._

trait TwoArgTriggerClause extends TriggerClause {
  val selector: HTMLSelectElement

  var argOne: HTMLSelectElement
  var argTwo: HTMLSelectElement

  val isLabel = BootStrapInput("is", disabled)
  val andLabel = BootStrapInput("and", disabled)
  val comparatorSelector = BootStrapSelector(TwoArgOp:::OneArgOp)

  comparatorSelector.onchange = _ => {
    if (TwoArgOp.contains(comparatorSelector.value)) {
      andLabel.removeAttribute("hidden")
      argTwo.removeAttribute("hidden")
    } else {
      andLabel.setAttribute("hidden", "")
      argTwo.setAttribute("hidden", "")
    }
  }

  override def renderAll: List[Element] = {
    List(selector, isLabel, comparatorSelector, argOne, andLabel, argTwo)
  }

  override def retrieveTrigger: String = {
    val argResult = if (argTwo.hasAttribute("hidden")) { argOne.value } else { s"${argOne.value} and ${argTwo.value}" }
    s"When value of ${selector.value} is ${comparatorSelector.value} $argResult"
  }
}
