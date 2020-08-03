package de.htwg.signalk.frontend

import org.scalajs.dom
import org.scalajs.dom.raw.XMLHttpRequest

object Request {

  val xhr = new XMLHttpRequest

  xhr.open("GET",
    "http://api.openweathermap.org/" +
      "data/2.5/weather?q=Singapore"
  )

//  xhr.onload = (e: dom.Event) => {
//    if (xhr.status == 200) {
//      target.appendChild(
//        pre(xhr.responseText).render
//      )
//    }
//  }

  xhr.send()
}
