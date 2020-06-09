package de.htwg.signalk.html

import de.htwg.signalk.html.Util.buildSelector
import org.scalajs.dom.Event
import scalatags.JsDom.all._

object ValueExpression {
  val valueSelector = buildSelector(List("Depth", "Battery", "Fuel", "Performance", "TWA", "TWD", "SOG", "STW", "AWS", "TWS", "VMG", "Air", "Water"))

  val singleArgOperatorOptions = List("above", "below")
  val twoArgOperatorOptions = List("between", "outside")

  val valueArgSelector = buildValueArgSelector()

  val valueArgOne = buildValueArgOne()
  val valueArgTwo = buildValueArgTwo()

  def buildValueExpression() = {
    span(
      id:="expression",
      span(
        valueSelector
      ),
      span(
        " is ",
        span(valueArgSelector),
        valueArgOne,
        valueArgTwo
      )
    ).render
  }

  private def buildValueArgSelector() = {
    val valueArgSelector = buildSelector(twoArgOperatorOptions:::singleArgOperatorOptions)

    valueArgSelector.addEventListener("change", { _: Event => {
      if (twoArgOperatorOptions.contains(valueArgSelector.value)) {
        valueArgTwo.removeAttribute("hidden")
      } else {
        valueArgTwo.setAttribute("hidden", "")
      }
    }})

    valueArgSelector
  }

  private def buildValueArgOne() = {
    span(buildSelector(List("2 m", "5 m", "10 m", "25 m", "50 m", "100 m"))).render
  }

  private def buildValueArgTwo() = {
    span(
      " and ",
      buildSelector(List("2 m", "5 m", "10 m", "25 m", "50 m", "100 m"))
    ).render
  }
}
