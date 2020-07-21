package de.htwg.signalk.html.selector.action
import org.scalajs.dom.Element
import org.scalajs.dom.raw.HTMLElement

class ResetClause(override val num: Int)  extends ActionClause(num: Int) {
  override def retrieveAction: String = ???

  override def render: HTMLElement = ???

  override def renderAll: List[Element] = ???
}
