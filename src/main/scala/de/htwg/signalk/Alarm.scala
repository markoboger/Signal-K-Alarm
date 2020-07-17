package de.htwg.signalk

import de.htwg.signalk.html.Index
import org.scalajs.dom.document
import scalatags.JsDom.all._

object Alarm {

  def main(args: Array[String]): Unit = {
    document.body.appendChild(div(id:="alarm-component").render)
    val idx = new Index
    idx.html
  }
}

