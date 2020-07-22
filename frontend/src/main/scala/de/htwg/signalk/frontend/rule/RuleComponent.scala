package de.htwg.signalk.frontend.rule

import de.htwg.signalk.frontend.HTMLComponent
import de.htwg.signalk.parser.{Rule, RuleParser}
import org.scalajs.dom

import scala.util.{Failure, Success, Try}
import com.karasiq.bootstrap4.Bootstrap.default._
import rx.Var
import scalaTags.all._

class RuleComponent(val initialText: String, val rulesContainer: RulesContainer) extends HTMLComponent {
  override val _id: String = "_"

  val parser: RuleParser = new RuleParser
  var rule: Option[Rule[_]] = None

  val ruleText = Var(initialText)

  val active = Var(true)
  val activateButton = Button(ButtonStyle.success)("A", onclick := Callback.onClick(_ => active.update(!active.now)), "active".classIf(active)).render
  val deactivateButton = Button(ButtonStyle.danger)("D", onclick := Callback.onClick(_ => ())).render
  val removeButton = Button(ButtonStyle.primary).renderTag("")("-", onclick := Callback.onClick(_ => rulesContainer.removeRule(this))).render
  val buttonGroup = ButtonGroup(ButtonGroupSize.default, activateButton, deactivateButton, removeButton)

  val ruleInput = textarea(ruleText.reactiveInput, style := "resize: none;", width := "100%", onchange := Callback.onSubmit(_ => parse(ruleText.now))).render

  val ruleComponent = FormInputGroup((), ruleInput, FormInputGroup.addon(buttonGroup))

  ruleText.trigger {
    ruleInput.style.setProperty("height", "auto")
    val height = if (ruleInput.scrollHeight < 52) { 52 } else { ruleInput.scrollHeight }
    ruleInput.style.setProperty("height", height + 4 + "px")
  }

  private def parse(input: String): Unit = {
    if (input == "") {
      return
    }
    Try(parser.parse(parser.rule, input)) match {
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

  parse(ruleText.now)

  override def render: dom.Element = ruleComponent.render
}
