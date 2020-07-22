package de.htwg.signalk.frontend.selector.action
import org.scalajs.dom.Element
import org.scalajs.dom.raw.HTMLElement

class RestartClause(override val num: Int)  extends ActionClause(num: Int) {
  override def retrieveAction: String = ???

  override def render: HTMLElement = ???

  override def renderAll: List[Element] = ???
}
