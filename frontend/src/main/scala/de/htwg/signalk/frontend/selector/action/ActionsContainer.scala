package de.htwg.signalk.frontend.selector.action

import de.htwg.signalk.frontend.{HTMLComponent, RuleEditor}
import org.scalajs.dom.raw.HTMLElement
import scalatags.JsDom.all._

class ActionsContainer extends HTMLComponent {
  val mainAction = new ActionComponent(true, this)
  var additionalActions: List[ActionComponent] = Nil

  override def render: HTMLElement = {
    div(
      mainAction.render,
      for(action <- additionalActions) yield action.render
    ).render
  }

  def addAction = RuleEditor.update(() => additionalActions = new ActionComponent(actionsContainer = this)::additionalActions)
  def removeAction(action: ActionComponent) = RuleEditor.update(() => additionalActions = additionalActions.filterNot(elm => elm == action))
  def retrieveAction = mainAction.retrieveAction + (for(action <- additionalActions) yield action.retrieveAction).mkString
}
