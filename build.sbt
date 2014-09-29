organization := "me.lessis"

name := "undelay"

version := "0.1.0"

description := "Satisfy Scala Futures quickly"

libraryDependencies ++= Seq(
  "me.lessis" %% "odelay-core" % "0.1.0",
  "org.scalatest" %% "scalatest" % "2.2.0" % "test")

licenses := Seq(
  ("MIT", url(s"https://github.com/softprops/${name.value}/blob/${version.value}/LICENSE")))

homepage := Some(url(s"https://github.com/softprops/${name.value}/#readme"))

crossScalaVersions := Seq("2.10.4", "2.11.1")

scalaVersion := crossScalaVersions.value.last

seq(bintraySettings:_*)

bintray.Keys.packageLabels in bintray.Keys.bintray := Seq("async", "reactive", "non-blocking", "future")

seq(lsSettings:_*)

LsKeys.tags in LsKeys.lsync := (bintray.Keys.packageLabels in bintray.Keys.bintray).value

externalResolvers in LsKeys.lsync := (resolvers in bintray.Keys.bintray).value

pomExtra := (
  <scm>
    <url>git@github.com:softprops/undelay.git</url>
    <connection>scm:git:git@github.com:softprops/undelay.git</connection>
  </scm>
  <developers>
    <developer>
      <id>softprops</id>
      <name>Doug Tangren</name>
      <url>https://github.com/softprops</url>
    </developer>
  </developers>)
