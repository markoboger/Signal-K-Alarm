package de.htwg.signalk.frontend.selector.action

import de.htwg.signalk.frontend.HTMLComponent
import org.scalajs.dom.raw.HTMLElement
import scalatags.JsDom.all._

class ActionsContainer extends HTMLComponent {
  override val _id: String = "actions-container"

  val mainAction = new ActionComponent(0, this)
  var additionalActions: List[ActionComponent] = Nil

  override def render: HTMLElement = {
    div(
      id := _id,
      mainAction.render,
      for(action <- additionalActions) yield action.render
    ).render
  }

  def addAction = update(() => additionalActions = new ActionComponent(additionalActions.length + 1, this)::additionalActions)
  def removeAction(action: ActionComponent) = update(() => additionalActions = additionalActions.filterNot(elm => elm == action))
  def retrieveAction = mainAction.retrieveAction + (for(action <- additionalActions) yield action.retrieveAction).mkString(" ")
}
