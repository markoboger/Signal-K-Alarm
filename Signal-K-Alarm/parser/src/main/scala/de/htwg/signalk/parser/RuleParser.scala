package de.htwg.signalk.parser

import scala.util.Try
import scala.util.parsing.input.CharSequenceReader

class RuleParser extends TriggerParser with ActionParser {
  def actions: Parser[List[Action]] = (action+) ~ ".*".r ^^ { case actions ~ rest => {
    rest.trim.length match {
      case 0 => actions
      case _ => throw Try(this.parse(action, new CharSequenceReader(rest))).failed.get
    }
  }}

  def rule = trigger ~ actions <~ opt("") ^^ { case trigger ~ actions => Rule(trigger, actions) }

  override def parse[T](p: Parser[T], in: java.lang.CharSequence): ParseResult[T] = {
    val parseResult = p(new CharSequenceReader(in))

    parseResult match {
      case Success(_, _) => parseResult
      case failure: NoSuccess => scala.sys.error(failure.msg)
    }
  }
}
