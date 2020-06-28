package de.htwg.signalk.html.trigger

import de.htwg.signalk.html.Util.buildSelectorWithID
import org.scalajs.dom.html.Span
import org.scalajs.dom.raw.HTMLSelectElement
import org.scalajs.dom.{Event, document}
import scalatags.JsDom.all._

object ValueExpression {
  val possibleValues = List("Depth", "Battery", "Fuel", "Performance", "TWA", "TWD", "SOG", "STW", "AWS", "TWS", "VMG", "Air", "Water")
  val possibleValueArgs: List[List[String]] = List(
    List("2 m", "5 m", "10 m", "25 m", "50 m", "100 m"), // Depth
    List("0 %", "10 %", "20 %", "50 %", "80 %", "100 %"), // Battery
    List("0 %", "10 %", "20 %", "50 %", "80 %", "100 %"), // Fuel
    List("0 %", "10 %", "20 %", "50 %", "80 %", "100 %"), // Performance
    List("0 °", "30 °", "60 °", "90 °", "120 °", "150 °", "180 °", "210 °", "240 °", "270 °", "300 °", "330 °"), // TWA
    List("0 °", "30 °", "60 °", "90 °", "120 °", "150 °", "180 °", "210 °", "240 °", "270 °", "300 °", "330 °"), // TWD
    List("0 kn", "1 kn", "2 kn", "5 kn", "10 kn", "20 kn", "40 kn", "60 kn"), // SOG
    List("0 kn", "1 kn", "2 kn", "5 kn", "10 kn", "20 kn", "40 kn", "60 kn"), // STW
    List("0 kn", "1 kn", "2 kn", "5 kn", "10 kn", "20 kn", "40 kn", "60 kn"), // AWS
    List("0 kn", "1 kn", "2 kn", "5 kn", "10 kn", "20 kn", "40 kn", "60 kn"), // TWS
    List("0 kn", "1 kn", "2 kn", "5 kn", "10 kn", "20 kn", "40 kn", "60 kn"), // VMG
    List("-10 °C", "0 °C", "10 °C", "20 °C", "30 °C", "40 °C", "50 °C", "80 °C", "100 °C"), // Air
    List("-10 °C", "0 °C", "10 °C", "20 °C", "30 °C", "40 °C", "50 °C", "80 °C", "100 °C") // Water
  )

  val singleArgOperatorOptions = List("above", "below")
  val twoArgOperatorOptions = List("between", "outside")

  def valueExpression = {
    val valueSelectors = span(
      " is ",
      span(
        valueArgSelector
      ),
      valueArgOne(possibleValueArgs.head),
      valueArgTwo(possibleValueArgs.head)
    ).render

    val valueSelector = this.valueSelector(valueSelectors)

    span(
      id:="trigger-expression",
      span(
        valueSelector
      ),
      valueSelectors
    ).render
  }

  private def valueSelector(valueSelectors: Span) = {
    val valueSelector = buildSelectorWithID(possibleValues, "valueSelector")

    valueSelector.addEventListener("change", { _: Event => {
      val selectedValue = valueSelector.value

      for (i <- possibleValues.indices) {
        if(possibleValues(i) == selectedValue) {
          val currentValueArgOne = document.getElementById("valueArgOne").parentNode
          val currentValueArgTwo = document.getElementById("valueArgTwo").parentNode
          valueSelectors.replaceChild(valueArgOne(possibleValueArgs(i)), currentValueArgOne)
          valueSelectors.replaceChild(valueArgTwo(possibleValueArgs(i)), currentValueArgTwo)
        }
      }
    }})

    valueSelector
  }

  private def valueArgSelector = {
    val valueArgSelector = buildSelectorWithID(twoArgOperatorOptions:::singleArgOperatorOptions, "valueArgSelector")

    valueArgSelector.addEventListener("change", { _: Event => {
      if (twoArgOperatorOptions.contains(valueArgSelector.value)) {
        document.getElementById("valueArgTwo").asInstanceOf[HTMLSelectElement].parentElement.removeAttribute("hidden")
      } else {
        document.getElementById("valueArgTwo").asInstanceOf[HTMLSelectElement].parentElement.setAttribute("hidden", "")
      }
    }})

    valueArgSelector
  }

  private def valueArgOne(values: List[String]) = {
    span(
      buildSelectorWithID(values, "valueArgOne")
    ).render
  }

  private def valueArgTwo(values: List[String]) = {
    span(
      " and ",
      buildSelectorWithID(values, "valueArgTwo")
    ).render
  }

  def retrieveValueExpression(): String = {
    val valueSelector = document.getElementById("valueSelector").asInstanceOf[HTMLSelectElement].value
    val valueArgSelector = document.getElementById("valueArgSelector").asInstanceOf[HTMLSelectElement].value
    val valueArgOne = document.getElementById("valueArgOne").asInstanceOf[HTMLSelectElement].value
    val valueArgTwo = document.getElementById("valueArgTwo").asInstanceOf[HTMLSelectElement]

    if (valueArgTwo.parentElement.hasAttribute("hidden")) {
      "When value of " + valueSelector + " is " + valueArgSelector + " " + valueArgOne
    } else {
      "When value of " + valueSelector + " is " + valueArgSelector + " " + valueArgOne + " and " + valueArgTwo.value
    }
  }
}
