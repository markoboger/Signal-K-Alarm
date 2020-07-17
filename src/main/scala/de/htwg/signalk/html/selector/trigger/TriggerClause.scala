package de.htwg.signalk.html.selector.trigger

import de.htwg.signalk.html.HTMLComponent

trait TriggerClause extends HTMLComponent {
  override final val _id = "trigger-clause"

  def retrieveTrigger: String
}
