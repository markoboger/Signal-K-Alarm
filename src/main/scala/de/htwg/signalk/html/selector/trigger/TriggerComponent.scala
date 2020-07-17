package de.htwg.signalk.html.selector.trigger

import de.htwg.signalk.html.HTMLComponent
import org.scalajs.dom.Event
import scalatags.JsDom.all._

class TriggerComponent extends HTMLComponent {
  override val _id: String = "trigger"

  var clause: TriggerClause = new TimeClause

  val selectValues = List("value of", "distance to", "time", "timer")

  val selector = select(for(opt <- selectValues) yield option(opt)).render

  selector.addEventListener("change", { _: Event => {
    update(() => selector.value match {
      case "value of" => clause = new ValueClause
      case "distance to" => clause = new DistanceClause
      case "time" => clause = new TimeClause
      case "timer" => clause = new TimerClause
    })
  }})

  def html = {
    div(
      id := _id,
      span("When ", selector),
      clause.html
    ).render
  }

  def retrieveTrigger = clause.retrieveTrigger
}
