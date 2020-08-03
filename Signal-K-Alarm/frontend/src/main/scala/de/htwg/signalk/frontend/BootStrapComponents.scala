package de.htwg.signalk.frontend

import org.scalajs.dom.raw.{HTMLButtonElement, HTMLDivElement, HTMLInputElement, HTMLSelectElement, HTMLTextAreaElement}
import scalatags.JsDom.all._

object BootStrapComponents {
  val ControlAreaElementMinHeight = 38
  val ControlAreaElementMinWidth = 75
  val TextAreaMinHeight = 52

  object BootStrapSelector {
    def apply(options: List[String], md: Modifier*): HTMLSelectElement = {
      select(for(opt <- options) yield option(opt), `class`:="custom-select",
        minWidth:=ControlAreaElementMinWidth, minHeight:=ControlAreaElementMinHeight)(md:_*).render
    }
  }

  object BootStrapInput {
    def apply(text: String, md: Modifier*): HTMLInputElement = {
      input(value:=text, `type`:="text", `class`:="form-control",
        minWidth:=ControlAreaElementMinWidth, minHeight:=ControlAreaElementMinHeight)(md:_*).render
    }
  }

  object BootStrapButton {
    def apply(label: String, style: String, md: Modifier*): HTMLButtonElement = {
      button(`class`:=s"btn btn-$style", `type`:="button")(label)(md:_*).render
    }
  }

  object BootStrapButtonGroup {
    def apply(md: Modifier*): HTMLDivElement = div(`class` := "btn-group flex-wrap")(md: _*).render
  }

  object BootStrapFormGroup {
    def apply(md: Modifier*): HTMLDivElement = div(`class`:="form-group")(md:_*).render
  }

  object BootStrapInputGroup {
    def apply(md: Modifier*): HTMLDivElement = div(`class`:="input-group")(md:_*).render
  }

  object BootStrapInputGroupAddon {
    def apply(md: Modifier*): HTMLDivElement = div(`class`:="input-group-addon")(md:_*).render
  }

  object GridLayout {
    private val sizingRow = (size: Int) => s"row-$size row-sm-$size row-md-$size row-lg-$size row-xl-$size"
    private val sizingCol = (size: Int) => s"col-$size col-sm-$size col-md-$size col-lg-$size col-xl-$size"

    def row(size: Int, md: Modifier*): HTMLDivElement = div(`class`:=sizingRow(size))(md:_*).render
    def col(size: Int, md: Modifier*): HTMLDivElement = div(`class`:=sizingCol(size))(md:_*).render
  }

  object AutoResizingTextArea {
    def apply(initialText: String, md: Modifier*): HTMLTextAreaElement = {
      val textAreaElement = textarea(initialText, style:="resize: none;")(md:_*).render
      textAreaElement.onkeydown = _ => resizeTextArea(textAreaElement)
      textAreaElement.onkeyup = _ => resizeTextArea(textAreaElement)
      textAreaElement
    }

    private def resizeTextArea(textarea: HTMLTextAreaElement): Unit = {
      textarea.style.setProperty("height", "auto")
      val height = if (textarea.scrollHeight < TextAreaMinHeight) { TextAreaMinHeight } else { textarea.scrollHeight }
      textarea.style.setProperty("height", s"${height+4}px")
    }
  }
}