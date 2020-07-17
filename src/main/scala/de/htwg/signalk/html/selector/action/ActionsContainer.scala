package de.htwg.signalk.html.selector.action

import de.htwg.signalk.html.HTMLComponent
import org.scalajs.dom.raw.HTMLElement
import scalatags.JsDom.all._

class ActionsContainer extends HTMLComponent {
  override val _id: String = "actions-container"

  val mainAction = new ActionComponent(0, this)
  var additionalActions: List[ActionComponent] = Nil

  override def html: HTMLElement = {
    div(
      id := _id,
      mainAction.html,
      for(action <- additionalActions) yield action.html
    ).render
  }

  def addAction = update(() => additionalActions = new ActionComponent(additionalActions.length + 1, this)::additionalActions)
  def removeAction(action: ActionComponent) = update(() => additionalActions = additionalActions.filterNot(elm => elm == action))
  def retrieveAction = mainAction.retrieveAction + (for(action <- additionalActions) yield action.retrieveAction).mkString(" ")
}
