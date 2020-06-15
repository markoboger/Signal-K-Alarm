package de.htwg.signalk.html.rule

import de.htwg.signalk.html.Util.buildSelector
import org.scalajs.dom.document
import org.scalajs.dom.raw.HTMLSelectElement
import scalatags.JsDom.all._

object TimeExpression {
  val timeArg = buildTimeArg()

  def buildTimeExpression() = {
    span(
      id:="rule-expression",
      span(
        " is ",
        timeArg
      )
    ).render
  }

  private def buildTimeArg() = {
    span(buildSelector(List("0:05", "-0:05"), "timerArg")).render
  }

  def retrieveTimeExpression(): String = {
    println("retrieve time expression")
    println(document.getElementById("timerArg").asInstanceOf[HTMLSelectElement].value)

    "When time is " + document.getElementById("timerArg").asInstanceOf[HTMLSelectElement].value
  }
}
