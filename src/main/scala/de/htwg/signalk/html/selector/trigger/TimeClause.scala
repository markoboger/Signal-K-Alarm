package de.htwg.signalk.html.selector.trigger

import de.htwg.signalk.html.Util.buildSelectorWithID
import org.scalajs.dom.raw.HTMLElement
import scalatags.JsDom.all._

class TimeClause extends TriggerClause {

  val timeArgHours = buildSelectorWithID(List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
    "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"), "timerArgHours")
  val timeArgMinutes = buildSelectorWithID(List("00", "10", "15", "20", "30", "40", "45", "50"), "timerArgMinutes")

  override def html: HTMLElement = {
    span(
      id := _id,
      span(
        " is ",
        span(timeArgHours),
        ":",
        span(timeArgMinutes)
      )
    ).render
  }

  override def retrieveTrigger: String = "When time is " + timeArgHours.value + ":" + timeArgMinutes.value
}
