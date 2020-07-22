package de.htwg.signalk.frontend.control

import de.htwg.signalk.frontend.HTMLComponent
import com.karasiq.bootstrap4.Bootstrap.default._
import org.scalajs.dom
import scalaTags.all._

class ControlButtonsComponent(val controlActions: ControlActions) extends HTMLComponent {
  val submitButton = Button(ButtonStyle.secondary)("Submit", onclick := Callback.onClick(_ => {
    controlActions.addRule(controlActions.retrieveRule.apply())
    controlActions.resetAction.apply()
  })).render
  val newActionButton = Button(ButtonStyle.secondary)("Add Action", onclick := Callback.onClick(_ => controlActions.addAction.apply())).render
  val newRuleButton = Button(ButtonStyle.secondary)("New Rule", onclick := Callback.onClick(_ => controlActions.addEmptyRule.apply())).render
  val resetEditorButton = Button(ButtonStyle.secondary)("Reset", onclick := Callback.onClick(_ => controlActions.resetAction.apply())).render
  val buttonGroup = ButtonGroup(ButtonGroupSize.default)(submitButton, newActionButton, newRuleButton, resetEditorButton)

  override val _id: String = "control-buttons"

  override def render: dom.Element = buttonGroup.apply(id:=_id, "flex-wrap".addClass, paddingBottom:="10px").render
}
