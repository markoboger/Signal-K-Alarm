package de.htwg.signalk.parser

import scala.util.parsing.input.CharSequenceReader

class RuleParser extends TriggerParser with ActionParser {
  def actions = action+

  def rule = trigger ~ actions ^^ { case trigger ~ actions => Rule(trigger, actions) }

  override def parse[T](p: Parser[T], in: java.lang.CharSequence): ParseResult[T] = {
    val parseResult = p(new CharSequenceReader(in))

    parseResult match {
      case Success(_, _) => parseResult
      case failure: NoSuccess => scala.sys.error(failure.msg)
    }
  }
}
