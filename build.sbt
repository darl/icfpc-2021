name := "icfpc-2021"

version := "0.1"

scalaVersion := "2.13.6"

idePackagePrefix := Some("icfpc21.classified")

val circeVersion = "0.14.1"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)
