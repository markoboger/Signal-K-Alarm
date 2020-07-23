package de.htwg.signalk.frontend.selector.action

import de.htwg.signalk.frontend.BootStrapComponents.BootStrapInput
import org.scalajs.dom.Element

class LogClause(override val isMainAction: Boolean)  extends ActionClause(isMainAction) {
  val logInput = BootStrapInput("")

  def retrieveAction: String = {
    val start = if (isMainAction) { ", then" } else { " and" }
    s"$start warn ${logInput.value}"
  }

  override def renderAll: List[Element] = List(logInput)
}
