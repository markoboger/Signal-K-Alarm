package de.htwg.signalk.parser

import squants.Time

trait Action {
  def activate()
}

case class DummyAction() extends Action {
  override def activate(): Unit = println("dummy")
}

trait SoundAmount {
  def getValue(): Int
}

case class SoundRepeat(numberOfTimes: Int) extends SoundAmount {
  override def getValue(): Int = numberOfTimes
}

case class SoundMax(time: Time) extends SoundAmount {
  override def getValue(): Int = time.value.toInt
}

case class SoundUntilChecked() extends SoundAmount {
  override def getValue(): Int = 9999
}

case class SoundAction(soundType: String, soundAmount: SoundAmount) extends Action {
  override def activate(): Unit = println("soundType: " + soundType + ", numberOfTimes: " + soundAmount)
}

case class WarnAction(warnMessage: String) extends Action {
  override def activate(): Unit = println("warn: " + warnMessage)
}

case class SendAction(sendRecipient: String, sendMessage: String) extends Action {
  override def activate(): Unit = println("send: " + sendMessage + " to " + sendRecipient)
}

case class LogAction(logMessage: String) extends Action {
  override def activate(): Unit = println("log: " + logMessage)
}

case class ReactivateAction(delay: Option[Time]) extends Action {
  override def activate(): Unit = println("delay: " + delay)
}

case class DeactivateAction() extends Action {
  override def activate(): Unit = println("deactivate")
}

case class ResetAction(resetTarget: String) extends Action {
  override def activate(): Unit = println("reset: " + resetTarget)
}
