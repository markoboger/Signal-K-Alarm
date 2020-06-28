package de.htwg.signalk.html.trigger

import de.htwg.signalk.html.Util.buildSelector
import org.scalajs.dom.raw.HTMLSelectElement
import org.scalajs.dom.{Event, document}
import scalatags.JsDom.all._

object DistanceExpression {
  def distanceSelector = buildSelector(List("Waypoint", "Photo", "Logbook Entry", "Mark"), "distanceSelector")

  val singleArgOperatorOptions = List("above", "below")
  val twoArgOperatorOptions = List("between", "outside")

  def distanceExpression = {
    span(
      id:="trigger-expression",
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

  private def distanceArgSelector = {
    val distanceArgSelector = buildSelector(twoArgOperatorOptions:::singleArgOperatorOptions, "distanceArgSelector")

    distanceArgSelector.addEventListener("change", { _: Event => {
      if (twoArgOperatorOptions.contains(distanceArgSelector.value)) {
        document.getElementById("distanceArgTwo").asInstanceOf[HTMLSelectElement].parentElement.removeAttribute("hidden")
      } else {
        document.getElementById("distanceArgTwo").asInstanceOf[HTMLSelectElement].parentElement.setAttribute("hidden", "")
      }
    }})

    distanceArgSelector
  }

  private def distanceArgOne = {
    span(
      buildSelector(List("100 m", "200 m", "500 m", "1 nm", "2 nm", "5 nm"), "distanceArgOne")
    ).render
  }

  private def distanceArgTwo = {
    span(
      " and ",
      buildSelector(List("100 m", "200 m", "500 m", "1 nm", "2 nm", "5 nm"), "distanceArgTwo")
    ).render
  }

  def retrieveDistanceExpression(): String = {
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
