package de.htwg.signalk.frontend.selector.action

import org.scalajs.dom.Element

class ActivateClause(override val isMainAction: Boolean) extends ActionClause(isMainAction) {
  override def retrieveAction: String = ???
  override def renderAll: List[Element] = ???
}
