package de.htwg.signalk

import org.scalajs.dom

import scalajs.js.annotation.JSExport
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import dom.ext.Ajax

import scalajs.js
import js.Dynamic.literal
import com.felstar.scalajs.vue._
import org.scalajs.dom.raw.HTMLElement

import js.annotation._

@JSExportTopLevel("example.Todo")
object Todo extends {

  @js.native
  trait DemoVue extends Vue{
    var title:String=js.native
    var n:Double=js.native
    var todos:js.Array[DemoVueTodo]=js.native
  }

  type DemoVueMethod=js.ThisFunction0[DemoVue,_]

  @js.native
  trait DemoVueTodo extends js.Object{
    var done:Boolean=js.native
    var content:String=js.native
  }

  object DemoVueTodo{
    def apply(done:Boolean,content:String)=literal(done=done,content=content).asInstanceOf[DemoVueTodo]
  }

  @JSExport
  // we return the DemoVue so we can use it back in JS
  // could have returned raw Vue of course
  def main():DemoVue = {

    val tasks=js.Array("Learn JavaScript","Learn Vue.js","Learn Scala.js", "Build Alarm")
    //
    def ts=new java.util.Date().toString

    Vue.component("my-component", literal(
      props=js.Array("myMsg"),
      template="<p>A custom component with msg {{myMsg}} <slot>default content</slot></p>"))

    Vue.directive("mydirective", literal(
      update=(el:HTMLElement, directive:Directive)=>{el.innerHTML="This comes from my-directive with contents "+directive.value+" and expression "+directive.expression}
    )
    )

    val demo = new Vue(
      literal(el="#demo",
        data=literal(
          message="Hello Vue.js!!!!!",
          title="Todo App",
          todos=tasks.map(content=>literal(done=content==tasks.head,content=content)),
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
          barValue= 100,
          n=0
        ),//,
        // js.ThisFunction would be fine, just trying to be more type specific
        methods=literal(clickHandler=((demoVue:DemoVue)=>demoVue.n-=1):DemoVueMethod,
          addTask=((demoVue:DemoVue)=>demoVue.todos.append(DemoVueTodo(false,s"new $ts"))):DemoVueMethod,
          change1st=((demoVue:DemoVue)=>Vue.set(demoVue.todos, 0,DemoVueTodo(false,ts))):DemoVueMethod,
          remove=((demoVue:DemoVue,idx:Int)=>Vue.delete(demoVue.todos,idx)):js.ThisFunction1[DemoVue,Int,_],
          flipAll=((demoVue:DemoVue)=>demoVue.todos.foreach(td=>td.done= !td.done)):DemoVueMethod
        ),
        computed=literal(todosComputed=(demoVue:DemoVue)=> demoVue.todos.map(_.content)),
        //
        filters=literal(reverse=((value:js.Any)=>value.toString.reverse),
          wrap=(value:js.Any,begin:String, end:String)=>begin+value.toString+end,
          extract=(array:js.Array[js.Dynamic],field:String)=>
            if (js.isUndefined(array)) array else array.map(_.selectDynamic(field))
        )
      )
    )

    demo.$watch("title",(newValue:String, oldValue:String) => println("changed "+newValue))

    val demoVue=demo.asInstanceOf[DemoVue]

    // filters declared above inline, can be also done as below
    //    Vue.filter("reverse", (value:js.Any)=>value.toString.reverse)
    //    Vue.filter("wrap", (value:js.Any,begin:String, end:String)=>begin+value.toString+end)

    // println(js.JSON.stringify(demo.$data))

    //demo.$log

    demoVue
  }
}