import sbt._
import sbt.impl.GroupArtifactID

object Versions {
  val Scopt               = "3.3.0"
  val Config              = "1.3.0"
  val Akka                = "2.4.9"
  val Jackson             = "2.7.5"
}

object Dependencies {
  def latest(artifact: GroupArtifactID): ModuleID = artifact % "latest.integration" changing()

  // scalastyle:off

  val akka                = Seq (
    "com.typesafe.akka" %% "akka-actor" % Versions.Akka,
    "com.typesafe.akka" %% "akka-remote" % Versions.Akka,
    "com.typesafe.akka" %% "akka-cluster" % Versions.Akka,
    "com.typesafe.akka" %% "akka-http-experimental" % Versions.Akka,
    "com.typesafe.akka" %% "akka-slf4j" % Versions.Akka
  )

  val jacksonDatabind     = "com.fasterxml.jackson.core" % "jackson-databind" % Versions.Jackson
  val jacksonDataformat   = "com.fasterxml.jackson.dataformat" % "jackson-dataformat-xml" % Versions.Jackson
  val jacksonScala        = "com.fasterxml.jackson.module" %% "jackson-module-scala" % Versions.Jackson

  // logging
  val slf4j               = "org.slf4j" % "slf4j-api" % "1.7.21"
  val logBack-core        = "ch.qos.logback" % "logback-core" % "1.1.7"
  val logBack-classic     = "ch.qos.logback" % "logback-classic" % "1.1.7"

 // configuration
  val scopt                 = "com.github.scopt"           %% "scopt"                       % Versions.Scopt
  val typsafeConfig         = "com.typesafe"                % "config"                      % Versions.Config

  // Tests
  val scalaMockSpecs    = "org.scalamock" %% "scalamock-specs2-support"     % "3.2.2"     % Test
  val specs2            = "org.specs2" %% "specs2-core"                     % "3.8.4"     % Test
  val scalaTest         = "org.scalatest" %% "scalatest"                    % "2.2.6"     % Test

  val junit             = "junit" % "junit"                                   % "4.11"    % Test

  val scalaTests = Seq(specs2, scalaMockSpecs)
  val javaTests = Seq(junit)

  // scalastyle:on
}