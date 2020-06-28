package de.htwg.signalk.parser

case class Rule[A<:AnyRef](t:Trigger[A],action:Action) {
  def execute( current:A ) = if (t.check(current)) action.activate()
}
