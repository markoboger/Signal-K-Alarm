package de.htwg.signalk.html.selector.trigger

import de.htwg.signalk.html.Util.buildSelectorWithID
import org.scalajs.dom.Event
import org.scalajs.dom.raw.{HTMLElement, HTMLSelectElement}
import scalatags.JsDom.all._

trait TwoArgTriggerClause extends TriggerClause {
  val selector: HTMLSelectElement

  val oneArgOptions = List("above", "below")
  val twoArgOptions = List("between", "outside")

  val argSelector = buildSelectorWithID(twoArgOptions:::oneArgOptions, "value-arg-selector")

  var argOne: HTMLSelectElement
  var argTwo: HTMLSelectElement

  argSelector.addEventListener("change", { _: Event => {
    if (twoArgOptions.contains(argSelector.value)) {
      argTwo.parentElement.removeAttribute("hidden")
    } else {
      argTwo.parentElement.setAttribute("hidden", "")
    }
  }})

  override def html(): HTMLElement = {
    span(
      id := _id,
      span(selector),
      span(
        " is ",
        span(argSelector),
        span(argOne),
        span(" and ", argTwo)
      )
    ).render
  }

  override def retrieveTrigger: String = {
    val argResult = if (argTwo.parentElement.hasAttribute("hidden")) { argOne.value } else { argOne.value + " and " + argTwo.value }
    "When value of " + selector.value + " is " + argSelector.value + " " + argResult
  }
}
