package de.htwg.signalk.frontend.selector.trigger

import de.htwg.signalk.frontend.{HTMLComponent, Util}
import org.scalajs.dom.Event
import com.karasiq.bootstrap4.Bootstrap.default._
import scalaTags.all._

class TriggerComponent(val parent: HTMLComponent) extends HTMLComponent {
  override val _id: String = "trigger"

  var clause: TriggerClause = new ValueClause
  val selectValues = List("value of", "distance to", "time", "timer")

  val selector = Util.buildCustomSelector(selectValues)
  val whenLabel = Util.buildCustomLabel("When")

  selector.addEventListener("change", { _: Event => {
    parent.update(() => selector.value match {
      case "value of" => clause = new ValueClause
      case "distance to" => clause = new DistanceClause
      case "time" => clause = new TimeClause
      case "timer" => clause = new TimerClause
    })
  }})

  def render = FormInputGroup((), id := _id, whenLabel, selector, for(element <- clause.renderAll) yield element).render

  def retrieveTrigger = clause.retrieveTrigger
}
