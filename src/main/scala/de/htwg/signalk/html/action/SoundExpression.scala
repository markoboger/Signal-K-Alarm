package de.htwg.signalk.html.action

import de.htwg.signalk.html.Util.buildSelector
import scalatags.JsDom.all._

object SoundExpression {
  def soundAction = buildSelector(List("once", "twice", "three times", "max 20 sec", "max 1 min", "until checked"), "distanceSelector")

  def buildSoundExpression() = {
    span(
      id:="action-expression",
      span(
        soundAction
      )
    ).render
  }
}
