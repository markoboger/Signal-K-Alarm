package de.htwg.signalk.frontend.control

import de.htwg.signalk.frontend.HTMLComponent
import de.htwg.signalk.frontend.BootStrapComponents.{BootStrapButton, BootStrapButtonGroup}
import scalatags.JsDom.all._

class ControlButtonsComponent(val controlActions: ControlActions) extends HTMLComponent {
  val submitButton = BootStrapButton("Submit", "secondary", onclick := { () => {
    controlActions.addRule(controlActions.retrieveRule.apply())
    controlActions.resetAction.apply()
  }})
  val newActionButton = BootStrapButton("Add Action", "secondary", onclick := { () => controlActions.addAction.apply() })
  val newRuleButton = BootStrapButton("New Rule", "secondary", onclick := { () => controlActions.addEmptyRule.apply() })
  val resetEditorButton = BootStrapButton("Reset", "secondary", onclick := { () => controlActions.resetAction.apply() })

  val buttonGroup = BootStrapButtonGroup(submitButton, newActionButton, newRuleButton, resetEditorButton, paddingBottom:="12px")

  override def render = buttonGroup
}
