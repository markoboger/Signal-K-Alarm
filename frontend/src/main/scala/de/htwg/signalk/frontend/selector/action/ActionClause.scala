package de.htwg.signalk.frontend.selector.action

import de.htwg.signalk.frontend.HTMLComponent
import org.scalajs.dom.Element
import scalatags.JsDom.all.span

abstract class ActionClause(val num: Int) extends HTMLComponent {
  override final val _id = "action-clause"
  override def render: Element = span().render

  def retrieveAction: String

  def renderAll: List[Element]
}
