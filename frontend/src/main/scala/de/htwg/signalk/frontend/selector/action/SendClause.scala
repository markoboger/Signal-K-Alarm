package de.htwg.signalk.frontend.selector.action
import de.htwg.signalk.frontend.Util
import org.scalajs.dom.Element
import rx.Var

class SendClause(override val num: Int)  extends ActionClause(num: Int) {
  val sendMessage = Var("")
  val sendInput = Util.buildCustomInput(sendMessage)
  val toLabel = Util.buildCustomLabel("to")
  val sendRecipient = Util.buildCustomSelector(List("me", "Skipper", "Obman", "Service"))

  def retrieveAction: String = {
    val start = if (num == 0) { ", then" } else { " and" }
    s"$start send ${sendMessage.now} to ${sendRecipient.value}"
  }

  override def renderAll: List[Element] = List(sendInput, toLabel, sendRecipient)
}
