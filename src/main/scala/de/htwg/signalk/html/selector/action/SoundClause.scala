package de.htwg.signalk.html.selector.action

import de.htwg.signalk.html.Util.buildSelectorWithID
import scalatags.JsDom.all._

class SoundClause(override val num: Int)  extends ActionClause(num: Int) {
  val selector = buildSelectorWithID(List("Alarm", "Beep", "Bell", "Horn", "Whistle"), _id + num)
  val soundArg = buildSelectorWithID(List("once", "twice", "three times", "max 20 sec", "max 1 min", "until checked"), "sound-arg-selector")

  def html = {
    span(
      id:="action-expression",
      span(selector),
      span(soundArg)
    ).render
  }

  def retrieveAction: String = {
    val start = if (num == 0) { ", then" } else { " and" }
    start + " sound " + selector.value + " " + soundArg.value
  }
}
