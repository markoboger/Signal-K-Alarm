package de.htwg.signalk.html.rule

import org.scalajs.dom.Event
import scalatags.JsDom.all._

object Rule {

  def createRule(ruleText: String) = {

    val ruleInput = input(
      size := "100"
    ).render

    ruleInput.value = ruleText

    ruleInput.addEventListener("change", { _: Event => {
      println(ruleInput.value)
    }})

    val activateButton = button("Activate",
      backgroundColor := "#4CAF50"
    ).render

    activateButton.addEventListener("click", { _: Event => {
      println("activate rule: " + ruleInput.value)
    }})

    val deactivateButton = button("Deactivate",
      backgroundColor := "#f44336"
    ).render

    deactivateButton.addEventListener("click", { _: Event => {
      println("deactivate rule: " + ruleInput.value)
    }})

    div(ruleInput,
      span(activateButton),
      span(deactivateButton)
    ).render
  }
}
