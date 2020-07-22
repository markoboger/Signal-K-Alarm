package de.htwg.signalk.frontend.selector.action

import de.htwg.signalk.frontend.{HTMLComponent, Util}
import com.karasiq.bootstrap4.Bootstrap.default._
import scalaTags.all._

class ActionComponent(val num: Int, val actionContainer: ActionsContainer) extends HTMLComponent {
  override val _id: String = s"action-$num"

  var clause: ActionClause = new SoundClause(num)
  val selectValues = List("sound", "warn", "log", "send", "activate", "deactivate", "reset", "restart")

  val selector = Util.buildCustomSelector(selectValues)

  val sentenceStart = if (num == 0) { ", then" } else { ", and" }
  val sentenceLabel = Util.buildCustomLabel(sentenceStart)

  selector.onchange = _ => {
    actionContainer.update(() => selector.value match {
      case "sound" => clause = new SoundClause(num)
      case "warn" => clause = new WarnClause(num)
      case "log" => clause = new LogClause(num)
      case "send" => clause = new SendClause(num)
      case "activate" => clause = new ActivateClause(num)
      case "deactivate" => clause = new DeactivateClause(num)
      case "reset" => clause = new ResetClause(num)
      case "restart" => clause = new RestartClause(num)
    })
  }

  val removeButton = Button(ButtonStyle.primary)("-", onclick := Callback.onClick(_ => removeAction)).render

  override def render = {
    if (num == 0) {
      FormInputGroup((), id := _id, sentenceLabel, selector, for(element <- clause.renderAll) yield element).render
    } else {
      FormInputGroup((), id := _id, sentenceLabel, selector, for(element <- clause.renderAll) yield element, removeButton).render
    }
  }

  def retrieveAction = clause.retrieveAction
  def removeAction = actionContainer.removeAction(this)
}
