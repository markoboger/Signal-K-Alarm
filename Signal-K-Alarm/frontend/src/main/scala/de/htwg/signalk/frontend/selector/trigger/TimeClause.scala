package de.htwg.signalk.frontend.selector.trigger

import de.htwg.signalk.frontend.BootStrapComponents.{BootStrapInput, BootStrapSelector}
import org.scalajs.dom.Element
import scalatags.JsDom.all._

class TimeClause extends TriggerClause {
  val hours = (1 to 23).toList.map(_.toString)
  val minutes = "00"::"05"::(10 to 55 by 5).toList.map(_.toString)

  val isLabel = BootStrapInput("is", disabled)
  val colonLabel = BootStrapInput(":", disabled)
  val argHours = BootStrapSelector(hours)
  val argMinutes = BootStrapSelector(minutes)

  override def retrieveTrigger: String = "When time is " + argHours.value + ":" + argMinutes.value

  override def renderAll: List[Element] = List(isLabel, argHours, colonLabel, argMinutes)
}
