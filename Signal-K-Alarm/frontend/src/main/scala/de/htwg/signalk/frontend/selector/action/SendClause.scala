package de.htwg.signalk.frontend.selector.action

import de.htwg.signalk.frontend.BootStrapComponents.{BootStrapInput, BootStrapSelector}
import de.htwg.signalk.parser.RuleConstants.SendRecipient
import org.scalajs.dom.Element
import scalatags.JsDom.all._

class SendClause(override val isMainAction: Boolean)  extends ActionClause(isMainAction) {
  val sendInput = BootStrapInput("")
  val toLabel = BootStrapInput("to", disabled)
  val sendRecipient = BootStrapSelector(SendRecipient)

  def retrieveAction: String = {
    val start = if (isMainAction) { ", then" } else { " and" }
    s"$start send ${sendInput.value} to ${sendRecipient.value}"
  }

  override def renderAll: List[Element] = List(sendInput, toLabel, sendRecipient)
}
