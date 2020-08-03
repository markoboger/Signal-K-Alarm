name := "Signal-K-Alarm"
version := "0.1"
scalaVersion := "2.12.10"

lazy val parser = project
  .in(file("parser"))
  .settings(
    libraryDependencies ++= parserDependencies.value
  )

lazy val parserjs = project
  .in(file("parser"))
  .settings(
    libraryDependencies ++= parserjsDependencies.value,
    target := file("frontend/target/parserjs")
  )
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)

lazy val frontend = project
  .settings(
    name := "frontend",
    jsSettings,
    libraryDependencies ++= frontendDependencies.value,
  )
  .dependsOn(parserjs)
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)

lazy val parserDependencies = Def.setting(Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
  "org.scalatest" %% "scalatest" % "3.1.0" % "test",
  "org.typelevel"  %% "squants"  % "1.6.0",
  "com.lihaoyi" %% "upickle" % "0.9.5"
))

lazy val parserjsDependencies = Def.setting(Seq(
  "org.scala-lang.modules" %%% "scala-parser-combinators" % "1.1.2",
  "org.scalatest" %%% "scalatest" % "3.1.0" % "test",
  "org.typelevel"  %%% "squants"  % "1.6.0",
  "com.lihaoyi" %%% "upickle" % "0.9.5"
))

lazy val frontendDependencies = Def.setting(Seq(
  "org.scala-js" %%% "scalajs-dom" % "1.0.0",
  "com.lihaoyi" %%% "scalatags" % "0.8.2"
))

lazy val jsSettings = Seq(
  scalaJSUseMainModuleInitializer := true,
  skip in packageJSDependencies := false,
  jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()
)

