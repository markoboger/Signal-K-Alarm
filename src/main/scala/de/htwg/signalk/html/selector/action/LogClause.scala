package de.htwg.signalk.html.selector.action
import de.htwg.signalk.html.Util
import org.scalajs.dom.Element
import rx.Var

class LogClause(override val num: Int)  extends ActionClause(num: Int) {
  val logMessage = Var("")
  val logInput = Util.buildCustomInput(logMessage)

  def retrieveAction: String = {
    val start = if (num == 0) { ", then" } else { " and" }
    start + " warn " + logMessage.now
  }

  override def renderAll: List[Element] = List(logInput)
}
