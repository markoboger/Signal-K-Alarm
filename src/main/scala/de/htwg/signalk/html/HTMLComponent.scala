package de.htwg.signalk.html

import org.scalajs.dom.{Element, document}
import org.scalajs.dom.raw.{HTMLDivElement, HTMLElement}

trait HTMLComponent {
  val _id: String

  def render: Element

  def update(callback: () => Unit): Unit = {
    callback()
    val element = document.getElementById(_id).asInstanceOf[HTMLElement]
    element.parentElement.replaceChild(render, element)
  }

  def update(): Unit = {
    val element = document.getElementById(_id).asInstanceOf[HTMLElement]
    element.parentElement.replaceChild(render, element)
  }
}
