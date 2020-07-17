package de.htwg.signalk.html

import org.scalajs.dom.html.{Element, Select}
import org.scalajs.dom.{Event, document}
import scalatags.JsDom.all._

object Util {
  def buildSelectorWithID(options: List[String], selectorId: String) = {
    select(
      id := selectorId,
      for(opt <- options) yield option(opt)
    ).render
  }

  def buildSelectorWithTag(options: List[String], selectorTag: String) = {
    val selector = select(for(opt <- options) yield option(opt)).render
    selector.classList.add(selectorTag)
    selector
  }

  def createOnChoiceSwitch(selector: Select, replaceElementId: String, replaceTargetElement: Element, selectValues: List[String], replaceElements: List[Element]): Unit = {
    selector.addEventListener("change", { _: Event => {
      val elementToReplace = Option(document.getElementById(replaceElementId))
      val selectedValue = selector.value

      for (i <- selectValues.indices) {
        if(selectValues(i) == selectedValue) {
          if (elementToReplace.isEmpty) {
            replaceTargetElement.appendChild(replaceElements(i))
          } else {
            replaceTargetElement.replaceChild(replaceElements(i), elementToReplace.get)
          }
        }
      }
    }})
  }
}
