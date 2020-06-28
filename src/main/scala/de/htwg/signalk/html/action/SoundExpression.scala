package de.htwg.signalk.html.action

import de.htwg.signalk.html.Util.buildSelectorWithID
import org.scalajs.dom.document
import org.scalajs.dom.raw.HTMLSelectElement
import scalatags.JsDom.all._

object SoundExpression {
  def soundAction = buildSelectorWithID(List("once", "twice", "three times", "max 20 sec", "max 1 min", "until checked"), "soundSelector")

  def buildSoundExpression() = {
    span(
      id:="action-expression",
      span(
        soundAction
      )
    ).render
  }

  def retrieveSoundExpression(): String = {
    ", then sound " + document.getElementById("soundSelector").asInstanceOf[HTMLSelectElement].value
  }
}
