organization := "me.lessis"

name := "undelay"

version := "0.1.0-SNAPSHOT"

description := "Satisfy Scala Futures more quickly"

libraryDependencies ++= Seq(
  "me.lessis" %% "odelay-core" % "0.1.0",
  "org.scalatest" %% "scalatest" % "2.2.0" % "test")
