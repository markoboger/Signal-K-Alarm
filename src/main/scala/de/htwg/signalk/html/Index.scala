package de.htwg.signalk.html

import de.htwg.signalk.html.Rule.buildRule
import org.scalajs.dom.document

object Index {
  def renderHtml(): Unit = {
    val rule = buildRule()

    document.body.appendChild(rule)
  }
}
