package de.htwg.signalk.frontend.selector.trigger

import de.htwg.signalk.frontend.Util
import org.scalajs.dom.raw.HTMLSelectElement

class DistanceClause extends TwoArgTriggerClause {
  override val selector = Util.buildCustomSelector(List("Waypoint", "Photo", "Logbook Entry", "Mark"))

  var argOne: HTMLSelectElement  = Util.buildCustomSelector(List("100 m", "200 m", "500 m", "1 nm", "2 nm", "5 nm"))
  var argTwo: HTMLSelectElement  = Util.buildCustomSelector(List("100 m", "200 m", "500 m", "1 nm", "2 nm", "5 nm"))
}
