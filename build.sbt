lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Hello",
    libraryDependencies ++= Seq(
      "org.scalaj" %% "scalaj-http" % "2.3.0",
      "org.scalatest" %% "scalatest" % "3.0.3" % "test"
    )
  )
