package de.htwg.signalk

import de.htwg.signalk.parser.RuleParser
import de.htwg.signalk.parser.ParserFailureMessages._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.{Failure, Success, Try}

class RuleParserSpec extends AnyWordSpec with Matchers {

  val parser = new RuleParser

  "A Rule 'rule' " should {
    "accept expressions of the form 'When value of Depth is below 5m, then sound Alarm once'" in {
      parser.parse(parser.rule, "When value of Depth is below 5m, then sound Alarm once").successful should be(true)
      parser.parse(parser.rule, "When value of Fuel is below 20%, then sound Alarm once and send 20% fuel left to me").successful should be(true)
    }

    "not accept invalid expressions" in {
      Try(parser.parse(parser.rule,"When value of Fuel is below 20%, then sound Ala once")).failed.get.getMessage should be(soundTypeFailureMessage)
      Try(parser.parse(parser.rule,"When value of Fuel is below 20%, then sound Alarm oxx times")).failed.get.getMessage should be(soundExpFailureMessage)
    }
  }
}
