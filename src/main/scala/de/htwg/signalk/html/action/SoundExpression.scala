package de.htwg.signalk.html.action

import scalatags.JsDom.all._

object SoundExpression {

  def buildSoundExpression() = {
    span(
      id:="action-expression",
      span(
        select(
          option("sfsfe")
        )
      )
    ).render
  }
}
