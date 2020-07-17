package de.htwg.signalk.html.selector.action

import de.htwg.signalk.html.HTMLComponent

abstract class ActionClause(val num: Int) extends HTMLComponent {
  override final val _id = "action-clause"

  def retrieveAction: String
}
