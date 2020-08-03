package de.htwg.signalk.frontend.selector.action

import org.scalajs.dom.Element

abstract class ActionClause(val isMainAction: Boolean) {
  def retrieveAction: String
  def renderAll: List[Element]
}
