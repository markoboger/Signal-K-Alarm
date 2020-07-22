package de.htwg.signalk.frontend.selector.trigger

import de.htwg.signalk.frontend.Util
import org.scalajs.dom.Element

class TimerClause extends TriggerClause {
  val timerValues = List("0:05", "-0:05")

  val isLabel = Util.buildCustomLabel("is")
  val argTimer = Util.buildCustomSelector(timerValues)

  override def retrieveTrigger: String = "When timer is " + argTimer.value

  override def renderAll: List[Element] = List(isLabel, argTimer)
}
