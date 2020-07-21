package de.htwg.signalk.html.selector.trigger

import de.htwg.signalk.html.Util
import org.scalajs.dom.Element
import org.scalajs.dom.raw.HTMLSelectElement

abstract class TwoArgTriggerClause extends TriggerClause {
  val selector: HTMLSelectElement

  val oneArgComparators = List("above", "below")
  val twoArgComparators = List("between", "outside")

  var argOne: HTMLSelectElement
  var argTwo: HTMLSelectElement

  val isLabel = Util.buildCustomLabel("is")
  val andLabel = Util.buildCustomLabel("and")
  val comparatorSelector = Util.buildCustomSelector(twoArgComparators:::oneArgComparators)

  comparatorSelector.onchange = _ => {
    if (twoArgComparators.contains(comparatorSelector.value)) {
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
    val argResult = if (argTwo.hasAttribute("hidden")) { argOne.value } else { argOne.value + " and " + argTwo.value }
    "When value of " + selector.value + " is " + comparatorSelector.value + " " + argResult
  }
}
