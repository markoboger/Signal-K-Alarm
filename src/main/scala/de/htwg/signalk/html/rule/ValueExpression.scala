package de.htwg.signalk.html.rule

import de.htwg.signalk.html.Util.buildSelector
import org.scalajs.dom.raw.HTMLSelectElement
import org.scalajs.dom.{Event, document}
import scalatags.JsDom.all._

object ValueExpression {
  val valueSelector = buildSelector(List("Depth", "Battery", "Fuel", "Performance", "TWA", "TWD", "SOG", "STW", "AWS", "TWS", "VMG", "Air", "Water"), "valueSelector")

  val singleArgOperatorOptions = List("above", "below")
  val twoArgOperatorOptions = List("between", "outside")

  val valueArgSelector = buildValueArgSelector()

  val valueArgOne = buildValueArgOne()
  val valueArgTwo = buildValueArgTwo()

  def buildValueExpression() = {
    span(
      id:="rule-expression",
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
    val valueArgSelector = buildSelector(twoArgOperatorOptions:::singleArgOperatorOptions, "valueArgSelector")

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
    span(buildSelector(List("2 m", "5 m", "10 m", "25 m", "50 m", "100 m"), "valueArgOne")).render
  }

  private def buildValueArgTwo() = {
    span(
      " and ",
      buildSelector(List("2 m", "5 m", "10 m", "25 m", "50 m", "100 m"), "valueArgTwo")
    ).render
  }

  def retrieveValueExpression(): String = {
    val valueSelector = document.getElementById("valueSelector").asInstanceOf[HTMLSelectElement].value
    val valueArgSelector = document.getElementById("valueArgSelector").asInstanceOf[HTMLSelectElement].value
    val valueArgOne = document.getElementById("valueArgOne").asInstanceOf[HTMLSelectElement].value
    val valueArgTwo = document.getElementById("valueArgTwo").asInstanceOf[HTMLSelectElement].value

    if (this.valueArgTwo.hasAttribute("hidden")) {
      "When value of " + valueSelector + " is " + valueArgSelector + " " + valueArgOne
    } else {
      "When value of " + valueSelector + " is " + valueArgSelector + " " + valueArgOne + " and " + valueArgTwo
    }
  }
}
