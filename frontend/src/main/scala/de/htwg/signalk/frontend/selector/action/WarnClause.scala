package de.htwg.signalk.frontend.selector.action
import de.htwg.signalk.frontend.Util
import org.scalajs.dom.Element
import rx.Var

class WarnClause(override val num: Int)  extends ActionClause(num: Int) {
  val warnMessage = Var("")
  val warnInput = Util.buildCustomInput(warnMessage)

  def retrieveAction: String = {
    val start = if (num == 0) { ", then" } else { " and" }
    s"$start warn ${warnMessage.now}"
  }

  override def renderAll: List[Element] = List(warnInput)
}
