package de.htwg.signalk.html.rule

import de.htwg.signalk.html.HTMLComponent
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
  val activateButton2 = Button(ButtonStyle.success)("A", onclick := Callback.onClick(_ => active.update(!active.now)), "active".classIf(active)).render
  val deactivateButton2 = Button(ButtonStyle.danger)("D", onclick := Callback.onClick(_ => ())).render
  val removeButton2 = Button(ButtonStyle.primary).renderTag("")("-", onclick := Callback.onClick(_ => rulesContainer.removeRule(this))).render
  val buttonGroup = ButtonGroup(ButtonGroupSize.default, activateButton2, deactivateButton2, removeButton2)

  val ruleInput = FormInputGroup.text(ruleText.reactiveInput, onchange := Callback.onSubmit(_ => parse(ruleText.now))).render

  val ruleComponent = FormInputGroup((), ruleInput, FormInputGroup.addon(buttonGroup))

  private def parse(input: String): Unit = {
    if (input == "") {
      return
    }
    Try(parser.parse(parser.rule, input)) match {
      case Success(rule) => {
        this.rule = Some(rule.get)
        ruleInput.setAttribute("style", "color: green;")
        dom.window.alert("Successfully added new rule: " + input)
      }
      case Failure(e) => {
        this.rule = None
        ruleInput.setAttribute("style", "color: red;")
        dom.window.alert("Rule couldn't be added: " + e.getMessage)
      }
    }
  }

  parse(ruleText.now)

  override def render: dom.Element = ruleComponent.render
}
