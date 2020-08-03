package de.htwg.signalk.parser

case class Rule[A<:AnyRef](t: Trigger[A], actions: List[Action]) {
  def activate( current:A ) = if (t.check(current)) actions.foreach(_.activate())
  def deactivate() = actions.foreach(_.deactivate())
}
