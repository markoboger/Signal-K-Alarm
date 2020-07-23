package de.htwg.signalk.frontend.selector.trigger

import de.htwg.signalk.frontend.BootStrapComponents.BootStrapSelector
import de.htwg.signalk.frontend.RuleEditor
import de.htwg.signalk.parser.RuleConstants._

class ValueClause extends TwoArgTriggerClause {
  var argOne = BootStrapSelector(List("2 m", "5 m", "10 m", "25 m", "50 m", "100 m"))
  var argTwo = BootStrapSelector(List("2 m", "5 m", "10 m", "25 m", "50 m", "100 m"))

  val selector = BootStrapSelector(DepthType:::PercentType:::AngleType:::SpeedType:::TempType)

  private val selectValueToArgMap = (
    (for(value <- DepthType) yield (value -> DepthExampleArgs))
    :::(for(value <- PercentType) yield (value -> PercentExampleArgs))
    :::(for(value <- AngleType) yield (value -> AngleExampleArgs))
    :::(for(value <- SpeedType) yield (value -> SpeedExampleArgs))
    :::(for(value <- TempType) yield (value -> TempExampleArgs))
    )
    .toMap

  selector.onchange = _ => {
    selectValueToArgMap.get(selector.value).foreach(possibleArg => {
      RuleEditor.update(() => {
        argOne = BootStrapSelector(possibleArg)
        argTwo = BootStrapSelector(possibleArg)
      })
    })
  }
}
