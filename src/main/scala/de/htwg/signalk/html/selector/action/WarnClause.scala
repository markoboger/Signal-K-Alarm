package de.htwg.signalk.html.selector.action
import de.htwg.signalk.html.Util
import org.scalajs.dom.Element
import rx.Var

class WarnClause(override val num: Int)  extends ActionClause(num: Int) {
  val warnMessage = Var("")
  val warnInput = Util.buildCustomInput(warnMessage)

  def retrieveAction: String = {
    val start = if (num == 0) { ", then" } else { " and" }
    start + " warn " + warnMessage.now
  }

  override def renderAll: List[Element] = List(warnInput)
}
