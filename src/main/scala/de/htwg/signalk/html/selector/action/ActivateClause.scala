package de.htwg.signalk.html.selector.action
import org.scalajs.dom.raw.HTMLElement

class ActivateClause(override val num: Int)  extends ActionClause(num: Int) {
  override def retrieveAction: String = ???

  override def html: HTMLElement = ???
}
