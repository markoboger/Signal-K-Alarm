package de.htwg.signalk.html.rule

import de.htwg.signalk.html.Util.buildSelector
import org.scalajs.dom.raw.HTMLSelectElement
import org.scalajs.dom.{Event, document}
import scalatags.JsDom.all._

object DistanceExpression {
  val distanceSelector = buildSelector(List("Waypoint", "Photo", "Logbook Entry", "Mark"), "distanceSelector")

  val singleArgOperatorOptions = List("above", "below")
  val twoArgOperatorOptions = List("between", "outside")

  val distanceArgSelector = buildValueArgSelector()

  val distanceArgOne = buildDistanceArgOne()
  val distanceArgTwo = buildDistanceArgTwo()

  def buildDistanceExpression() = {
    span(
      id:="rule-expression",
      span(
        distanceSelector
      ),
      span(
        " is ",
        span(distanceArgSelector),
        distanceArgOne,
        distanceArgTwo
      )
    ).render
  }

  private def buildValueArgSelector() = {
    val valueArgSelector = buildSelector(twoArgOperatorOptions:::singleArgOperatorOptions, "distanceArgSelector")

    valueArgSelector.addEventListener("change", { _: Event => {
      if (twoArgOperatorOptions.contains(valueArgSelector.value)) {
        distanceArgOne.removeAttribute("hidden")
      } else {
        distanceArgTwo.setAttribute("hidden", "")
      }
    }})

    valueArgSelector
  }

  private def buildDistanceArgOne() = {
    span(buildSelector(List("2 m", "5 m", "10 m", "25 m", "50 m", "100 m"), "distanceArgOne")).render
  }

  private def buildDistanceArgTwo() = {
    span(
      " and ",
      buildSelector(List("2 m", "5 m", "10 m", "25 m", "50 m", "100 m"), "distanceArgTwo")
    ).render
  }

  def retrieveValueExpression(): String = {
    val distanceSelector = document.getElementById("distanceSelector").asInstanceOf[HTMLSelectElement].value
    val distanceArgSelector = document.getElementById("distanceArgSelector").asInstanceOf[HTMLSelectElement].value
    val distanceArgOne = document.getElementById("distanceArgOne").asInstanceOf[HTMLSelectElement].value
    val distanceArgTwo = document.getElementById("distanceArgTwo").asInstanceOf[HTMLSelectElement]

    if (distanceArgTwo.hasAttribute("hidden")) {
      "When value of " + distanceSelector + " is " + distanceArgSelector + " " + distanceArgOne
    } else {
      "When value of " + distanceSelector + " is " + distanceArgSelector + " " + distanceArgOne + " and " + distanceArgTwo.value
    }
  }
}
