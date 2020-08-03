package de.htwg.signalk.frontend.selector.trigger

import de.htwg.signalk.frontend.BootStrapComponents.{BootStrapInput, BootStrapSelector}
import org.scalajs.dom.Element
import scalatags.JsDom.all._

class TimerClause extends TriggerClause {
  val timerValues = List("0:05", "-0:05")

  val isLabel = BootStrapInput("is", disabled)
  val argTimer = BootStrapSelector(timerValues)

  override def retrieveTrigger: String = "When timer is " + argTimer.value
  override def renderAll: List[Element] = List(isLabel, argTimer)
}
