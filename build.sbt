commonSmlBuildSettings

ossPublishSettings

organization := "com.softwaremill.undelay"

name := "undelay"

version := "0.2.0"

description := "Satisfy Scala Futures quickly"

libraryDependencies ++= Seq(
  "com.softwaremill.odelay" %% "odelay-core" % "0.3.0",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test")

crossScalaVersions := Seq("2.11.12", "2.12.6")

scalaVersion in ThisBuild := crossScalaVersions.value.last
