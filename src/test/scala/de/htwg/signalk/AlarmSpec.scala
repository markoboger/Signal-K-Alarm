package de.htwg.signalk

import org.scalatest._
import matchers.should.Matchers

class RuleParserSpec extends WordSpec with Matchers{

  "A Signal-K RuleParser" should {
    val parser = new RuleParser
    "accept simple timer rules" in {
      parser.parse(parser.rule, "When timer is 0:00").toString should be ("When")
    }
  }

}
