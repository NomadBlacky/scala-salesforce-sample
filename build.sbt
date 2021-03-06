lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "org.nomadblacky",
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "scala-salesforce-sample",
    libraryDependencies ++= Seq(
      "org.scalaj" %% "scalaj-http" % "2.3.0",
      "io.spray" %%  "spray-json" % "1.3.3",
      "org.scalatest" %% "scalatest" % "3.0.3" % "test"
    )
  )
