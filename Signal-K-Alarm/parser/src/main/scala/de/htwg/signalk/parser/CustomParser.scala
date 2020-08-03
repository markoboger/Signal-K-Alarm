package de.htwg.signalk.parser

import scala.util.parsing.combinator.RegexParsers

trait CustomParser extends RegexParsers {
  def orExpressionFromList(list: List[String]) = list.map(literal).reduce(_ | _)
}
