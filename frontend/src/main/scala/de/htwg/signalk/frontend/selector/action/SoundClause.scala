package de.htwg.signalk.frontend.selector.action

import de.htwg.signalk.frontend.BootStrapComponents.BootStrapSelector
import de.htwg.signalk.parser.RuleConstants.{SoundType, SoundExampleArgs}
import org.scalajs.dom.Element

class SoundClause(override val isMainAction: Boolean)  extends ActionClause(isMainAction) {
  val selector = BootStrapSelector(SoundType)
  val soundArg = BootStrapSelector(SoundExampleArgs)

  def retrieveAction: String = {
    val start = if (isMainAction) { ", then" } else { " and" }
    s"$start sound ${selector.value} ${soundArg.value}"
  }

  override def renderAll: List[Element] = List(selector, soundArg)
}
