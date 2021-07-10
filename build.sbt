name := "icfpc-2021"

version := "0.1"

scalaVersion := "2.13.6"

val circeVersion = "0.14.1"

libraryDependencies ++= Seq(
"io.circe" %% "circe-core",
"io.circe" %% "circe-generic",
"io.circe" %% "circe-parser"
).map(_ % circeVersion)

idePackagePrefix := Some("icfpc21.classified")

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % "test"

libraryDependencies +=
  "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.3"
