package de.htwg.signalk.html.selector.trigger
import de.htwg.signalk.html.Util.buildSelectorWithID
import org.scalajs.dom.raw.HTMLElement
import scalatags.JsDom.all._

class TimerClause extends TriggerClause {
  val timerArg = buildSelectorWithID(List("0:05", "-0:05"), "timerArg")

  override def html: HTMLElement = {
    span(
      id := _id,
      span(" is ", span(timerArg))
    ).render
  }

  override def retrieveTrigger: String = "When timer is " + timerArg.value
}
