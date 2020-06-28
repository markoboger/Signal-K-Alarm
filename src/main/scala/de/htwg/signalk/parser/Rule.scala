package de.htwg.signalk.parser

case class OrderOperator[A<:Ordered[A]](){
  def above(static:A)(current:A):Boolean = { static<current}
  def below(static:A)(current:A):Boolean = { static>current }
  def between(b:A, c:A)(a:A):Boolean = { b<a && a<c }
  def outside(b:A, c:A)(a:A):Boolean = { b>a || a>c }
}

case class TimeOperator[Time]() {
  def is(static:Time)(current:Time):Boolean = { static==current }
}

class Trigger[A](cause:String, f:A => Boolean) {
  def trigger(static:A) = f(static)
  def check(current:A)  = trigger(current)
}

case class Rule[A<:AnyRef](t:Trigger[A],action:Action) {
  def execute( current:A ) = if (t.check(current)) action.activate()
}
