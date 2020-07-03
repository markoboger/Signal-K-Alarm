package de.htwg.signalk

import de.htwg.signalk.parser.RuleParser
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class RuleParserSpec extends AnyWordSpec with Matchers {

  val parser = new RuleParser

  "A Rule 'rule' " should {
    "accept expressions of the form 'When value of Depth is below 5m, then sound Alarm once'" in {
      parser.parse(parser.rule, "When value of Depth is below 5m, then sound Alarm once").successful should be(true)
      parser.parse(parser.rule, "When value of Fuel is below 20%, then sound Alarm once and send 20% fuel left to me").successful should be(true)
    }
  }
}
