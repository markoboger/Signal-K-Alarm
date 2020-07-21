package de.htwg.signalk.html

import com.karasiq.bootstrap4.Bootstrap.default._
import org.scalajs.dom.raw.HTMLSelectElement
import rx.Var
import scalaTags.all._

object Util {
  def buildCustomSelector(options: List[String]): HTMLSelectElement = {
    select(
      minWidth := 75, minHeight := 38,
      `class`:="custom-select",
      for(opt <- options) yield option(opt)
    ).render
  }

  def buildCustomLabel(text: String): Element = {
    val input = FormInputGroup.text(minWidth := 75, minHeight := 38, Var(text).reactiveInput).render
    input.setAttribute("disabled", "")
    input
  }

  def buildCustomInput(text: Var[String]): Element = FormInputGroup.text(minWidth := 75, minHeight := 38, text.reactiveInput).render
}
