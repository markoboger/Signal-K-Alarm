package de.htwg.signalk.frontend.selector.trigger

import de.htwg.signalk.frontend.BootStrapComponents.BootStrapSelector
import org.scalajs.dom.raw.HTMLSelectElement
import de.htwg.signalk.parser.RuleConstants.{DistanceExampleValues, DistanceType}

class DistanceClause extends TwoArgTriggerClause {
  override val selector = BootStrapSelector(DistanceType)

  var argOne: HTMLSelectElement  = BootStrapSelector(DistanceExampleValues)
  var argTwo: HTMLSelectElement  = BootStrapSelector(DistanceExampleValues)
}
