
import Dependencies._
import Resolvers._
import sbt.Keys._

name := "sgbplus"

lazy val commonSettings = Seq(
  version := "0.1",

  organization := "net.gemelen.dev",
  scalaVersion := "2.11.8",

  resolvers ++= Seq(
    sonatype,
    scalaz,
    typeSafe
  ),

  // use cached version for non changing libraries
  updateOptions := updateOptions.value.withCachedResolution(cachedResoluton = true),

  //pluginDirectory := file("project"),
  topLevelDirectory := None,
  crossPaths := false,
  exportJars := true,
  fork := true
)

def makeCommandLineProject(project: Project): Project =
  project.
    settings(commonSettings: _*).
    settings(
      libraryDependencies ++= scalaTests ++ logging ++ Seq(typesafeConfig)
    )

def makeLibraryProject(project: Project): Project =
  project.
    settings(commonSettings: _*).
    settings(autoScalaLibrary := true). // don't include scala library to clients)
    settings(libraryDependencies ++= scalaTests)

// Projects
val `net-gemelen-dev-sgbplus` = project.in(file(".")).
  settings(commonSettings: _*).
  settings(
    Seq(
      publish := false
    )
  ).
  aggregate(projects: _*)

lazy val projects = Seq[ProjectReference](
  `common`,
  `gate`
)

lazy val `common` = makeLibraryProject(project).
  settings(
    name := "common",
    libraryDependencies ++= Seq()
  )

lazy val `gate` = makeCommandLineProject(project).
  dependsOn(`common`).
  settings(
    name := "gate",
    mainClass in Compile := Some("net.gemelen.dev.sgbplus.gate.Main"),
    libraryDependencies ++= akka ++ Seq()
  )
