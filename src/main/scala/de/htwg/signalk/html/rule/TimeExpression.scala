package de.htwg.signalk.html.rule

import de.htwg.signalk.html.Util.buildSelector
import org.scalajs.dom.document
import org.scalajs.dom.raw.HTMLSelectElement
import scalatags.JsDom.all._

object TimeExpression {
  def timeExpression = {
    span(
      id:="rule-expression",
      span(
        " is ",
        timeArgHours,
        ":",
        timeArgMinutes
      )
    ).render
  }

  private def timeArgHours = {
    span(
      buildSelector(List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
        "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"), "timerArgHours")
    ).render
  }

  private def timeArgMinutes = {
    span(
      buildSelector(List("00", "10", "15", "20", "30", "40", "45", "50"), "timerArgMinutes")
    ).render
  }

  def retrieveTimeExpression(): String = {
    val hours = document.getElementById("timerArgHours").asInstanceOf[HTMLSelectElement].value
    val minutes = document.getElementById("timerArgMinutes").asInstanceOf[HTMLSelectElement].value

    "When time is " + hours + ":" + minutes
  }
}
