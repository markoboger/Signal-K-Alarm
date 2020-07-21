package de.htwg.signalk.html.selector.trigger

import de.htwg.signalk.html.Util
import org.scalajs.dom.Element

class TimeClause extends TriggerClause {

  val hours = List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
    "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23")

  val minutes = List("00", "10", "15", "20", "30", "40", "45", "50")

  val timeArgHours = Util.buildCustomSelector(hours)
  val timeArgMinutes = Util.buildCustomSelector(minutes)

  val isLabel = Util.buildCustomLabel("is")
  val colonLabel = Util.buildCustomLabel(":")
  val argHours = Util.buildCustomSelector(hours)
  val argMinutes = Util.buildCustomSelector(minutes)


  override def retrieveTrigger: String = "When time is " + timeArgHours.value + ":" + timeArgMinutes.value

  override def renderAll: List[Element] = List(isLabel, argHours, colonLabel, argMinutes)
}
