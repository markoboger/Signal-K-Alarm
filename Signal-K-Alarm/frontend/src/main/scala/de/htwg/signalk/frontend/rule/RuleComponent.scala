package de.htwg.signalk.frontend.rule

import de.htwg.signalk.frontend.HTMLComponent
import de.htwg.signalk.frontend.BootStrapComponents.{AutoResizingTextArea, BootStrapButton, BootStrapButtonGroup, BootStrapFormGroup, BootStrapInputGroup, BootStrapInputGroupAddon}
import de.htwg.signalk.parser.{Rule, RuleParser}
import org.scalajs.dom

import scala.util.{Failure, Success, Try}
import scalatags.JsDom.all._

class RuleComponent(val initialText: String, val rulesContainer: RulesContainer) extends HTMLComponent {
  val parser: RuleParser = new RuleParser
  var rule: Option[Rule[_]] = None

  val activateButton = BootStrapButton("A", "success")
  val deactivateButton = BootStrapButton("D", "danger")
  val removeButton = BootStrapButton("-", "primary", onclick := { () => rulesContainer.removeRule(this) })
  val buttonGroup = BootStrapButtonGroup(activateButton, deactivateButton, removeButton)

  val ruleInput = AutoResizingTextArea(initialText, width := "100%", onchange := { () => parse() })

  val ruleComponent = BootStrapFormGroup(BootStrapInputGroup(ruleInput, BootStrapInputGroupAddon(buttonGroup)))

  private def parse(): Unit = {
    if (ruleInput.value == "") {
      return
    }
    Try(parser.parse(parser.rule, ruleInput.value)) match {
      case Success(rule) => {
        this.rule = Some(rule.get)
        ruleInput.style.setProperty("color", "green")
        dom.window.alert(s"Successfully added new rule: $rule")
      }
      case Failure(e) => {
        this.rule = None
        ruleInput.style.setProperty("color", "red")
        dom.window.alert(s"Rule couldn't be added: ${e.getMessage}")
      }
    }
  }

  parse()

  override def render = ruleComponent
}
