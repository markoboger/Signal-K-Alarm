package de.htwg.signalk.html

import scalatags.JsDom.all._

object TimeExpression {

  def buildTimeExpression() = {
    span(
      id:="expression",
      span(
        select(
          option("sfsfe")
        )
      )
    ).render
  }
}
