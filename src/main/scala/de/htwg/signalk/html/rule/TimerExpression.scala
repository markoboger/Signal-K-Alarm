package de.htwg.signalk.html.rule

import de.htwg.signalk.html.Util.buildSelector
import scalatags.JsDom.all._
import org.scalajs.dom.document
import org.scalajs.dom.raw.HTMLSelectElement

object TimerExpression {
  def timerExpression = {
    span(
      id:="rule-expression",
      span(
        " is ",
        timerArg
      )
    ).render
  }

  private def timerArg = {
    span(buildSelector(List("0:05", "-0:05"), "timerArg")).render
  }

  def retrieveTimerExpression(): String = {
    "When timer is " + document.getElementById("timerArg").asInstanceOf[HTMLSelectElement].value
  }
}
