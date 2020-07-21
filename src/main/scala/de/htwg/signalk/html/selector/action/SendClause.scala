package de.htwg.signalk.html.selector.action
import de.htwg.signalk.html.Util
import org.scalajs.dom.Element
import rx.Var

class SendClause(override val num: Int)  extends ActionClause(num: Int) {
  val sendMessage = Var("")
  val sendInput = Util.buildCustomInput(sendMessage)
  val toLabel = Util.buildCustomLabel("to")
  val sendRecipient = Util.buildCustomSelector(List("me", "Skipper", "Obman", "Service"))

  def retrieveAction: String = {
    val start = if (num == 0) { ", then" } else { " and" }
    start + " send " + sendMessage.now + " to " + sendRecipient.value
  }

  override def renderAll: List[Element] = List(sendInput, toLabel, sendRecipient)
}
