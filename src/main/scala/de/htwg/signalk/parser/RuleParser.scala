package de.htwg.signalk.parser

class RuleParser extends TriggerParser with ActionParser {
  def actions = action+
  def rule = trigger ~ actions ^^ { case trigger ~ actions => Rule(trigger, actions) }
}
