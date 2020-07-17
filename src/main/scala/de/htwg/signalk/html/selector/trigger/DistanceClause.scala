package de.htwg.signalk.html.selector.trigger

import de.htwg.signalk.html.Util.buildSelectorWithID
import org.scalajs.dom.{Event, document}
import org.scalajs.dom.raw.{HTMLElement, HTMLSelectElement}
import scalatags.JsDom.all._

class DistanceClause extends TwoArgTriggerClause {
  override val selector = buildSelectorWithID(List("Waypoint", "Photo", "Logbook Entry", "Mark"), "distance-selector")

  var argOne: HTMLSelectElement  = buildSelectorWithID(List("100 m", "200 m", "500 m", "1 nm", "2 nm", "5 nm"), "distance-arg-one")
  var argTwo: HTMLSelectElement  = buildSelectorWithID(List("100 m", "200 m", "500 m", "1 nm", "2 nm", "5 nm"), "distance-arg-two")
}
