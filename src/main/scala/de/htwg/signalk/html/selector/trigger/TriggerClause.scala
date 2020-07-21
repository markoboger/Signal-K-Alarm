package de.htwg.signalk.html.selector.trigger

import de.htwg.signalk.html.HTMLComponent
import org.scalajs.dom
import org.scalajs.dom.Element
import scalatags.JsDom.all.span

trait TriggerClause extends HTMLComponent {
  override final val _id = "trigger-clause"

  override def render: Element = span().render

  def retrieveTrigger: String
  def renderAll: List[dom.Element]
}
