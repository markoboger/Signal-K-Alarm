package de.htwg.signalk.frontend.selector.trigger

import de.htwg.signalk.frontend.{HTMLComponent, RuleEditor}
import de.htwg.signalk.parser.RuleConstants.TriggerType
import de.htwg.signalk.frontend.BootStrapComponents.{BootStrapFormGroup, BootStrapInput, BootStrapInputGroup, BootStrapInputGroupAddon, BootStrapSelector}
import scalatags.JsDom.all._

class TriggerComponent extends HTMLComponent {
  var clause: TriggerClause = new ValueClause
  val typeToClauseMap = Map("value of" -> new ValueClause, "distance to" -> new DistanceClause, "time" -> new TimeClause, "timer" -> new TimerClause)

  val selector = BootStrapSelector(TriggerType)
  val whenLabel = BootStrapInput("When", disabled)

  selector.onchange = _ => { RuleEditor.update(() => clause = typeToClauseMap.getOrElse(selector.value, clause)) }

  def render = BootStrapFormGroup(BootStrapInputGroup(whenLabel, selector, for(element <- clause.renderAll) yield element))


  def retrieveTrigger = clause.retrieveTrigger
}
