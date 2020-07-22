package de.htwg.signalk.frontend.selector.action

import de.htwg.signalk.frontend.Util
import org.scalajs.dom.Element

class SoundClause(override val num: Int)  extends ActionClause(num: Int) {
  val selector = Util.buildCustomSelector(List("Alarm", "Beep", "Bell", "Horn", "Whistle"))
  val soundArg = Util.buildCustomSelector(List("once", "twice", "three times", "max 20 sec", "max 1 min", "until checked"))

  def retrieveAction: String = {
    val start = if (num == 0) { ", then" } else { " and" }
    s"$start sound ${selector.value} ${soundArg.value}"
  }

  override def renderAll: List[Element] = List(selector, soundArg)
}
