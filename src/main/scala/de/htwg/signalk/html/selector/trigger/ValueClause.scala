package de.htwg.signalk.html.selector.trigger

import de.htwg.signalk.html.Util.buildSelectorWithID
import org.scalajs.dom.{Event, document}
import org.scalajs.dom.raw.{HTMLElement, HTMLSelectElement}
import scalatags.JsDom.all._

class ValueClause extends TwoArgTriggerClause {
  val possibleValues = List("Depth", "Battery", "Fuel", "Performance", "TWA", "TWD", "SOG", "STW", "AWS", "TWS", "VMG", "Air", "Water")
  override val selector = buildSelectorWithID(possibleValues, "value-selector")

  val possibleValueArgs: List[List[String]] = List(
    List("2 m", "5 m", "10 m", "25 m", "50 m", "100 m"), // Depth
    List("0 %", "10 %", "20 %", "50 %", "80 %", "100 %"), // Battery
    List("0 %", "10 %", "20 %", "50 %", "80 %", "100 %"), // Fuel
    List("0 %", "10 %", "20 %", "50 %", "80 %", "100 %"), // Performance
    List("0 °", "30 °", "60 °", "90 °", "120 °", "150 °", "180 °", "210 °", "240 °", "270 °", "300 °", "330 °"), // TWA
    List("0 °", "30 °", "60 °", "90 °", "120 °", "150 °", "180 °", "210 °", "240 °", "270 °", "300 °", "330 °"), // TWD
    List("0 kn", "1 kn", "2 kn", "5 kn", "10 kn", "20 kn", "40 kn", "60 kn"), // SOG
    List("0 kn", "1 kn", "2 kn", "5 kn", "10 kn", "20 kn", "40 kn", "60 kn"), // STW
    List("0 kn", "1 kn", "2 kn", "5 kn", "10 kn", "20 kn", "40 kn", "60 kn"), // AWS
    List("0 kn", "1 kn", "2 kn", "5 kn", "10 kn", "20 kn", "40 kn", "60 kn"), // TWS
    List("0 kn", "1 kn", "2 kn", "5 kn", "10 kn", "20 kn", "40 kn", "60 kn"), // VMG
    List("-10 °C", "0 °C", "10 °C", "20 °C", "30 °C", "40 °C", "50 °C", "80 °C", "100 °C"), // Air
    List("-10 °C", "0 °C", "10 °C", "20 °C", "30 °C", "40 °C", "50 °C", "80 °C", "100 °C") // Water
  )

  var argOne: HTMLSelectElement = buildSelectorWithID(List("2 m", "5 m", "10 m", "25 m", "50 m", "100 m"), "value-arg-one")
  var argTwo: HTMLSelectElement = buildSelectorWithID(List("2 m", "5 m", "10 m", "25 m", "50 m", "100 m"), "value-arg-two")

  selector.addEventListener("change", { _: Event => {
    for (i <- possibleValues.indices) {
      if(possibleValues(i) == selector.value) {
        update(() => {
          argOne = buildSelectorWithID(possibleValueArgs(i), "value-arg-one")
          argTwo = buildSelectorWithID(possibleValueArgs(i), "value-arg-two")
        })
      }
    }
  }})
}
