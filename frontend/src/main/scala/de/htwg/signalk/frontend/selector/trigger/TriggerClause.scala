package de.htwg.signalk.frontend.selector.trigger

import org.scalajs.dom

trait TriggerClause {
  def retrieveTrigger: String
  def renderAll: List[dom.Element]
}
