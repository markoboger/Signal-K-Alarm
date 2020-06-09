package de.htwg.signalk.html

import scalatags.JsDom.all._

object Util {
  def buildSelector(options: List[String]) = {
    select(
      for(opt <- options) yield
        option(opt)
    ).render
  }
}
