package de.htwg.signalk.html

import org.scalajs.dom.document
import org.scalajs.dom.raw.{HTMLDivElement, HTMLElement}

trait HTMLComponent {
  val _id: String

  def html: HTMLElement

  def update(callback: () => Unit): Unit = {
    callback()
    println("id: " + _id)
    val element = document.getElementById(_id).asInstanceOf[HTMLElement]
    element.parentElement.replaceChild(html, element)
  }

  def update(): Unit = {
    val element = document.getElementById(_id).asInstanceOf[HTMLElement]
    element.parentElement.replaceChild(html, element)
  }
}
