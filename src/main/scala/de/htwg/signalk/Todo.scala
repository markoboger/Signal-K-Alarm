package de.htwg.signalk

import org.scalajs.dom

import scalajs.js.annotation.JSExport
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import dom.ext.Ajax

import scalajs.js
import js.Dynamic.literal
import com.felstar.scalajs.vue._
import org.querki.jquery.$
import org.scalajs.dom.raw.HTMLElement

import js.annotation._

@JSExportTopLevel("signalk.RuleEditor")
object RuleEditor extends {

  @js.native
  trait RuleVue extends Vue{
    var rules:js.Array[Rule]=js.native
  }

  type DemoVueMethod=js.ThisFunction0[RuleVue,_]

  @js.native
  trait Rule extends js.Object{
    var active:Boolean=js.native
    var content:String=js.native
  }

  object Rule{
    def apply(active:Boolean,content:String)=literal(active=active,content=content).asInstanceOf[Rule]
  }

  @JSExport
  def main():RuleVue = {

    val rules=js.Array("When timer is -0:05, sound Beep once, warn 'Start in 5 min'",
    "When timer is -0:04, sound Beep twice, warn 'Start in 4 min'",
    "When timer is -0:01, sound Beep three times, warn 'Start in 1 min'",
    "When timer is  0:00, sound Gong once, warn 'Start now', reset Timer(-0:05), restart Timer",

    "When value of Depth is below 10 m, sound Beep once, warn 'Entering shallow waters', deactivate",
    "When value of Depth is below 2.50 m, sound	LongBeep twice, warn 'Shallow water', reacitvate",
    "When value of Log is above 1000 nm, sound Beep three times, warn 'Check Oil', log",
    "When value of Fuel is below 20%, warn 'Fuel is low', reactivate",
    "When value of Fuel is below 10%, sound Beep once, warn 'Refuel'",
    "When value of TWA is outside 150 and 210, sound	Alarm1	3 min,warn 'Wind direction has changed', reactivate",
    "When distance to Marker17 is below	1 nm, sound  Beep once, warn 'Approaching Marker 17', deactivate",
    "When distance to Anker is above 100 m, sound Alarm1	until checked, warn 'Anker drifting', reactivate",

    "When timer is 0:20 sound Beep once, warn 'Time for Lookout' reset Timer, start Timer",
      "When time is 0:00 reset Daylog",
    "When value of Battery is below	20%, send 'Charge Battery' to me, reactivate after 1:00",
    "When value of TWS is above 40kn, send 'Boat in Storm' to me, reactivate after 1:00)")

    def ts=new java.util.Date().toString

    Vue.component("my-component", literal(
      props=js.Array("myMsg"),
      template="<p>A custom component with msg {{myMsg}} <slot>default content</slot></p>"))

    Vue.directive("mydirective", literal(
      update=(el:HTMLElement, directive:Directive)=>{el.innerHTML="This comes from my-directive with contents "+directive.value+" and expression "+directive.expression}
    )
    )

    val ruleComponent = new Vue(
      literal(el="#AlarmRuleEditor",
        data=literal(
          rules=rules.map(content=>literal(active=content==rules.head,content=content)),
          expressionOptions=js.Array("value of", "distance to", "time", "timer"),
          valueOptions=js.Array("Depth", "Battery","Fuel","Performance","TWA","TWD", "SOG", "STW", "AWS", "TWS", "VMG", "Air", "Water"),
          operatorOptions = js.Array("below", "above", "between", "outside"),
          depthOptions = js.Array("2 m", "5 m", "10 m", "25 m", "50 m", "100 m"),
          percentageOptions = js.Array("0 %","10 %", "20 %", "50 %", "90 %", "100 %"),
          degreeOptions = js.Array("0 °", "30 °", "60 °", "90 °", "120 °", "150 °", "180 °", "210 °", "240 °", "270 °", "300 °", "330 °"),
          speedOptions = js.Array("0 kn", "1 kn", "2 kn", "5 kn", "10 kn", "20 kn", "40 kn", "60 kn"),
          temperatureOptions = js.Array("-10 °C", "0 °C", "10 °C", "20 °C", "30 °C", "40 °C", "50 °C", "80 °C", "100 °C"),
          markerOptions = js.Array("Waypoint", "Photo", "LogEntry", "Mark"),
          distanceOptions = js. Array("100 m", "200 m", "500 m", "1 nm", "2 nm", "5 nm"),
          hourOptions = js.Array("0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23"),
          minuteOptions = js.Array("00","10","15","20","30","40","45","50"),
          actionOptions = js.Array("sound","warn","log","send","deactivate","reactivate","reset","restart"),
          soundOptions = js.Array("Alarm","Beep","Bell","Horn","Whistle"),
          repeatOptions = js.Array("once", "twice","three times","max 20 sec", "max 1 min", "max 3 min", "max 10 min", "until checked"),
          addresseeOptions = js.Array("me","Skipper","Obman","Owner","Service"),
          reactivateOptions = js.Array("after 0:01", "after 0:05", "after 1:00"),
          resetOptions = js.Array("Timer to 0:00", "Timer to -0:05", "Timer to -0:10","Timer to -0:20", "Timer to -1:00"),
        ),
        methods=literal(
          addRule=((demoVue:RuleVue)=>demoVue.rules.append(Rule(true,s"new $ts"))):DemoVueMethod,
          change1st=((demoVue:RuleVue)=>Vue.set(demoVue.rules, 0,Rule(true,ts))):DemoVueMethod,
          remove=((demoVue:RuleVue,idx:Int)=>Vue.delete(demoVue.rules,idx)):js.ThisFunction1[RuleVue,Int,_],
          flipAll=((demoVue:RuleVue)=>demoVue.rules.foreach(td=>td.active= !td.active)):DemoVueMethod
        ),
        computed=literal(todosComputed=(demoVue:RuleVue)=> demoVue.rules.map(_.content)),

        filters=literal(reverse=((value:js.Any)=>value.toString.reverse),
          wrap=(value:js.Any,begin:String, end:String)=>begin+value.toString+end,
          extract=(array:js.Array[js.Dynamic],field:String)=>
            if (js.isUndefined(array)) array else array.map(_.selectDynamic(field))
        )
      )
    )

    //ruleComponent.$watch("select",(newValue:String, oldValue:String) => println("changed "+newValue))
    val ruleVue=ruleComponent.asInstanceOf[RuleVue]
    //$("select").change(() => {println("Selection changed!")})//selectionChanged})
    ruleVue
  }
}