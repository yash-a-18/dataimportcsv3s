import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import sbt._

object Dependencies {
  val zioVersion = "2.0.13"
  val zioJsonVersion = "0.6.2"
  val zioHttpVersion = "3.0.0-RC7"
  val scalaLoggingVersion = "3.9.4"
  val betterfilesVersion = "3.9.2"
  val csv3sVersion = "0.1.3"

  val zioHttp     = "dev.zio" %% "zio-http"     % zioHttpVersion
  val zioTypesafeConfig  = "dev.zio" %% "zio-config-typesafe" % "4.0.1"  
  val zioLogging = "dev.zio" %% "zio-logging" % "2.2.1"
  val zioTest     = "dev.zio" %% "zio-test"     % zioVersion % Test
  val zioTestSBT = "dev.zio" %% "zio-test-sbt" % zioVersion % Test
  val zioTestMagnolia = "dev.zio" %% "zio-test-magnolia" % zioVersion % Test  

  
  val scalajsdom  = Def.setting {
    Seq("org.scala-js" %%% "scalajs-dom" % "2.4.0")
  }

  val zioJson = Def.setting {
    Seq("dev.zio" %%% "zio-json" % zioJsonVersion)
  }

  val scalaLogging :  Def.Initialize[Seq[ModuleID]] = Def.setting {
    Seq(
      "ch.qos.logback" % "logback-classic" % "1.2.10",
      "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion    )
  }

  val scalatest   :     Def.Initialize[Seq[ModuleID]] = Def.setting {
    Seq(
      "org.scalactic" %%% "scalactic"  % DependencyVersions.scalatest,
      "org.scalatest" %%% "scalatest" % DependencyVersions.scalatest % "test"
    )
  }

  val betterfiles = "com.github.pathikrit" %% "better-files" % betterfilesVersion

  val patientfilterparserjs: Def.Initialize[Seq[ModuleID]] = Def.setting{
    Seq(
      "com.axiom" %%% "patientfilterparserjs" % "0.0.1-SNAPSHOT"
    )
  }

  val laminar : Def.Initialize[Seq[ModuleID]] = Def.setting {
    Seq(
      "com.raquo" %%% "laminar" % DependencyVersions.laminar
    )
  }

  val csv3s = "ru.johnspade" %% "csv3s" % csv3sVersion


}
