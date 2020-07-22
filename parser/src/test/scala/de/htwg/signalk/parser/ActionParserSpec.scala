package de.htwg.signalk.parser

import de.htwg.signalk.parser.RuleConstants._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import squants.time.{Minutes, Seconds}

import scala.util.Try

class ActionParserSpec extends AnyWordSpec with Matchers{

  val parser = new RuleParser

  "A Sound Expression 'soundExp' " should {
    "accept expressions of the form 'once'" in {
      parser.parse(parser.soundAmount, "once").get should be(SoundRepeat(1))
      parser.parse(parser.soundAmount, "twice").get should be(SoundRepeat(2))
    }
    "accept expressions of the form 'three times'" in {
      parser.parse(parser.soundAmount, "three times").get should be(SoundRepeat(3))
      parser.parse(parser.soundAmount, "eight times").get should be(SoundRepeat(8))
    }
    "accept expressions of the form '3 times'" in {
      parser.parse(parser.soundAmount, "6 times").get should be(SoundRepeat(6))
      parser.parse(parser.soundAmount, "24 times").get should be(SoundRepeat(24))
    }
    "accept expressions of the form 'until checked'" in {
      parser.parse(parser.soundAmount, "until checked").get should be(SoundUntilChecked())
    }
    "accept expressions of the form 'max 23 min'" in {
      parser.parse(parser.soundAmount, "max 23 min").get should be(SoundMax(Minutes(23)))
      parser.parse(parser.soundAmount, "max 2 seconds").get should be(SoundMax(Seconds(2)))
    }
  }

  "A Sound Clause 'soundClause' " should {
    "accept expressions of the form 'sound Alarm once'" in {
      parser.parse(parser.soundClause, "sound Alarm once").get should be(SoundAction("Alarm", SoundRepeat(1)))
      parser.parse(parser.soundClause, "sound Horn eight times").get should be(SoundAction("Horn", SoundRepeat(8)))
      parser.parse(parser.soundClause, "sound Beep 71 times").get should be(SoundAction("Beep", SoundRepeat(71)))
      parser.parse(parser.soundClause, "sound Whistle max 4 min").get should be(SoundAction("Whistle", SoundMax(Minutes(4))))
      parser.parse(parser.soundClause, "sound Bell until checked").get should be(SoundAction("Bell", SoundUntilChecked()))
    }
  }

  "A Sound Clause 'soundClause' " should {
    "return the correct failure message with wrong sound type" in {
      Try(parser.parse(parser.soundClause, "sound Ala once").get).failed.get.getMessage should be(SoundTypeFailureMessage)
      Try(parser.parse(parser.soundClause, "sound Alarm th").get).failed.get.getMessage should be(SoundAmountFailureMessage)
    }
  }


  "A Warn Clause 'warnClause' " should {
    "accept expressions of the form 'warn this is a warn message" in {
      parser.parse(parser.warnClause, "warn this is a warn message").get should be(WarnAction("this is a warn message"))
      parser.parse(parser.warnClause, "warn fuel is below 20%").get should be(WarnAction("fuel is below 20%"))
    }
  }

  "A Send Clause 'sendClause' " should {
    "accept expressions of the form 'send this is a send message to me" in {
      parser.parse(parser.sendClause, "send this is a send message to me").get should be(SendAction("me", "this is a send message"))
      parser.parse(parser.sendClause, "send this is a send message to Skipper").get should be(SendAction("Skipper", "this is a send message"))
      parser.parse(parser.sendClause, "send this is a send message to Obman").get should be(SendAction("Obman", "this is a send message"))
      parser.parse(parser.sendClause, "send fuel is below 20% to Service").get should be(SendAction("Service", "fuel is below 20%"))
    }
  }

  "A Log Clause 'logClause' " should {
    "accept expressions of the form 'log log this message" in {
      parser.parse(parser.logClause, "log log this message").get should be(LogAction("log this message"))
    }
  }

  "A Reactivate Clause 'reactivate' " should {
    "accept expressions of the form 'reactivate" in {
      parser.parse(parser.reactivateClause, "reactivate").get should be(ReactivateAction(None))
    }
    "accept expressions of the form 'reactivate after 1 min" in {
      parser.parse(parser.reactivateClause, "reactivate after 1 min").get should be(ReactivateAction(Some(Minutes(1))))
      parser.parse(parser.reactivateClause, "reactivate after 14 min").get should be(ReactivateAction(Some(Minutes(14))))
      parser.parse(parser.reactivateClause, "reactivate after 30 sec").get should be(ReactivateAction(Some(Seconds(30))))
    }
    "accept expressions of the form 'reactivate after 12:52" in {
      parser.parse(parser.reactivateClause, "reactivate after 02:30").get should be(ReactivateAction(Some(Minutes(2)+Seconds(30))))
      parser.parse(parser.reactivateClause, "reactivate after 2:30").get should be(ReactivateAction(Some(Minutes(2)+Seconds(30))))
      parser.parse(parser.reactivateClause, "reactivate after 0:30").get should be(ReactivateAction(Some(Seconds(30))))
    }
  }

  "An Action" should {
    "accept Sound clauses" in {
      parser.parse(parser.action, ", then sound Alarm once").get should be(SoundAction("Alarm", SoundRepeat(1)))
      parser.parse(parser.action, "and sound Beep ten times").get should be(SoundAction("Beep", SoundRepeat(10)))
      parser.parse(parser.action, "and sound Horn until checked").get should be(SoundAction("Horn", SoundUntilChecked()))
    }
    "accept Warn clauses" in {
      parser.parse(parser.action, "and warn this is a warn message").get should be(WarnAction("this is a warn message"))
      parser.parse(parser.action, ", then warn a warn message").get should be(WarnAction("a warn message"))
    }
    "accept Send clauses" in {
      parser.parse(parser.action, "and send this is a send message to Skipper").get should be(SendAction("Skipper", "this is a send message"))
      parser.parse(parser.action, ", then send message for Obman to Obman").get should be(SendAction("Obman", "message for Obman"))
    }
    "accept Log clauses" in {
      parser.parse(parser.action, "and log log this message").get should be(LogAction("log this message"))
      parser.parse(parser.action, ", then log log this message").get should be(LogAction("log this message"))
    }
    "accept Reactivate clauses" in {
      parser.parse(parser.action, "and reactivate").get should be(ReactivateAction(None))
      parser.parse(parser.action, ", then reactivate after 10 min").get should be(ReactivateAction(Some(Minutes(10))))
      parser.parse(parser.action, ", then reactivate after 10:30").get should be(ReactivateAction(Some(Minutes(10)+Seconds(30))))
    }
  }
}
