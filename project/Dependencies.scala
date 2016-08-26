import sbt._
import sbt.impl.GroupArtifactID

object Versions {
  val Scopt               = "3.3.0"
  val Config              = "1.3.0"
  val Akka                = "2.4.9"
}

object Dependencies {
  def latest(artifact: GroupArtifactID): ModuleID = artifact % "latest.integration" changing()

  // scalastyle:off

  val jacksonDatabind     = "com.fasterxml.jackson.core" % "jackson-databind" % "2.3.5"
  val jacksonDataformat   = "com.fasterxml.jackson.dataformat" % "jackson-dataformat-xml" % "2.3.5"
  val jacksonScala        = "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.5.3"

  // apache
  val apacheCommonsLang   = "org.apache.commons" % "commons-lang3" % "3.2"
  val apacheCompress      = "org.apache.commons" % "commons-compress" % "1.9"
  val apacheHttpClient    = "org.apache.httpcomponents" % "httpclient" % "4.5.1"
  val apacheIO            = "commons-io" % "commons-io" % "2.4"
  val apacheCommonsPool   = "org.apache.commons" % "commons-pool2" % "2.3"

  val amazonAws           = "com.amazonaws" % "aws-java-sdk" % "1.6.4" excludeAll(
    ExclusionRule(organization = "org.javassist", name = "javassist"),
    ExclusionRule(organization = "org.apache.httpcomponents", name = "httpclient"))

  // misc
  val quartz              = "org.quartz-scheduler" % "quartz" % "2.2.1"
  val guava               = "com.google.guava" % "guava" % "18.0"
  val javaxMail           = "javax.mail" % "mail" % "1.4.7"
  val userAgentTools      = "eu.bitwalker" % "UserAgentUtils" % "1.19"

  // logging
  val log4j               = "org.slf4j" % "slf4j-api" % "1.7.10"

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