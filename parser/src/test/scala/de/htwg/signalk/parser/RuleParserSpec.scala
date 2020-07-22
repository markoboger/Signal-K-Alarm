package de.htwg.signalk.parser

import de.htwg.signalk.parser.RuleConstants._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.{Failure, Success, Try}

class RuleParserSpec extends AnyWordSpec with Matchers {

  val parser = new RuleParser

  "Actions 'actions' " should {
    "accept multiple actions in the form ', then sound Beep once and sound Beep once'" in {
      parser.parse(parser.actions, ", then sound Beep once and sound Beep once").successful should be(true)
      parser.parse(parser.actions, ", then sound Beep once and sound Beep once and sound Beep once and sound Beep once").successful should be(true)
      parser.parse(parser.actions, ", then sound Horn twice and send this message to me").successful should be(true)
    }
    "accept a single action in the form ', then sound Beep once'" in {
      parser.parse(parser.actions, ", then sound Alarm once").successful should be(true)
      parser.parse(parser.actions, ", then send this message to me").successful should be(true)
    }
    "not accept if one action is incorrect in the form ', then sound Beep once'" in {
      Try(parser.parse(parser.actions, ", then sound Beepg once")).isFailure should be(true)
      Try(parser.parse(parser.actions, ", then sound Beep once and sound Beepg once")).isFailure should be(true)
      Try(parser.parse(parser.actions, ", then sound Beep once and sound Beep once and sound Beep oce")).isFailure should be(true)
      Try(parser.parse(parser.actions, ", then sound Beep once and sound Beep oce and sound Beep once")).isFailure should be(true)
      Try(parser.parse(parser.actions, ", then sound Beep onc and sound Beep once and sound Beep once")).isFailure should be(true)
    }
  }

  "A Rule 'rule' " should {
    "accept expressions of the form 'When value of Depth is below 5m, then sound Alarm once'" in {
      parser.parse(parser.rule, "When value of Fuel is below 20%, then sound Alarm once and send 20% fuel left to me").successful should be(true)
      parser.parse(parser.rule, "When value of Depth is between 1 m and 4 m, then sound Alarm once and sound Alarm once").successful should be(true)
    }

    "not accept invalid expressions" in {
      Try(parser.parse(parser.rule,"When value of Fuel is below 20%, then sound Ala once")).failed.get.getMessage should be(SoundTypeFailureMessage)
      Try(parser.parse(parser.rule,"When value of Fuel is below 20%, then sound Alarm oxx times")).failed.get.getMessage should be(SoundAmountFailureMessage)
    }
  }
}
