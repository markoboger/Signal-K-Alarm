package de.htwg.signalk.html.rule

import de.htwg.signalk.html.HTMLComponent
import de.htwg.signalk.parser.{Rule, RuleParser}
import org.scalajs.dom
import org.scalajs.dom.Event
import org.scalajs.dom.raw.HTMLElement
import scalatags.JsDom.all._

import scala.util.{Failure, Success, Try}

class RuleSentenceComponent(val ruleText: String, val rulesContainer: RulesContainer) extends HTMLComponent {
  override val _id: String = "gre"

  var rule: Option[Rule[_]] = None
  val parser: RuleParser = new RuleParser

  val ruleInput = input(size := "100").render
  ruleInput.value = ruleText
  parse()

  ruleInput.addEventListener("change", { _: Event => {
    parse()
  }})

  val activateButton = button("Activate", backgroundColor := "#4CAF50").render

  activateButton.addEventListener("click", { _: Event => {
    //rule.map(rule => rule.activate())
    println("activate rule: " + ruleInput.value)
  }})

  val deactivateButton = button("Deactivate", backgroundColor := "#f44336").render

  deactivateButton.addEventListener("click", { _: Event => {
    //rule.map(rule => rule.deactivate())
    println("deactivate rule: " + ruleInput.value)
  }})

  val removeButton = button("-").render

  removeButton.addEventListener("click", { _: Event => {
    rulesContainer.removeRule(this)
  }})

  private def parse(): Unit = {
    if (ruleInput.value == "") {
      return
    }
    Try(parser.parse(parser.rule, ruleInput.value)) match {
      case Success(rule) => {
        this.rule = Some(rule.get)
        dom.window.alert("Successfully added new rule: " + ruleInput.value)
      }
      case Failure(e) => {
        this.rule = None
        dom.window.alert("Rule couldn't be added: " + e.getMessage)
      }
    }
  }

  override def html: HTMLElement = {
    div(ruleInput,
      span(activateButton),
      span(deactivateButton),
      span(removeButton)
    ).render
  }
}
