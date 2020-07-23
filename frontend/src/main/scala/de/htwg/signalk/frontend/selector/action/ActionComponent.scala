package de.htwg.signalk.frontend.selector.action

import de.htwg.signalk.frontend.{HTMLComponent, RuleEditor}
import de.htwg.signalk.frontend.BootStrapComponents.{BootStrapButton, BootStrapFormGroup, BootStrapInput, BootStrapInputGroup, BootStrapSelector}
import de.htwg.signalk.parser.RuleConstants.ActionType
import scalatags.JsDom.all._

class ActionComponent(val isMainAction: Boolean = false, val actionsContainer: ActionsContainer) extends HTMLComponent {
  var clause: ActionClause = new SoundClause(isMainAction)
  val typeToClauseMap = Map("sound" -> new SoundClause(isMainAction), "warn" -> new WarnClause(isMainAction), "log" -> new LogClause(isMainAction),
    "send" -> new SendClause(isMainAction), "activate" -> new ActivateClause(isMainAction), "deactivate" -> new DeactivateClause(isMainAction),
    "reset" -> new ResetClause(isMainAction), "restart" -> new RestartClause(isMainAction))

  val selector = BootStrapSelector(ActionType)

  val sentenceStart = if (isMainAction) { ", then" } else { ", and" }
  val sentenceLabel = BootStrapInput(sentenceStart, disabled)
  val removeButton = BootStrapButton("-", "primary", onclick := { () => removeAction }).render

  selector.onchange = _ => { RuleEditor.update(() => clause = typeToClauseMap.getOrElse(selector.value, clause)) }

  override def render = {
    if (isMainAction) {
      BootStrapFormGroup(BootStrapInputGroup(sentenceLabel, selector, for(element <- clause.renderAll) yield element))
    } else {
      BootStrapFormGroup(BootStrapInputGroup(sentenceLabel, selector, for(element <- clause.renderAll) yield element, removeButton))
    }
  }

  def retrieveAction = clause.retrieveAction
  def removeAction = actionsContainer.removeAction(this)
}
