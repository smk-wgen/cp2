import sbt._
import Keys._
import play.Project._
import cloudbees.Plugin._

object ApplicationBuild extends Build {

  val appName         = "cp2"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "mysql" % "mysql-connector-java" % "5.1.18",
    "com.google.code.gson" % "gson" % "1.7.1",
    "org.apache.commons" % "commons-io" % "1.3.2"
  )

  // Add your own project settings here
  val main = play.Project(appName, appVersion, appDependencies).settings(cloudBeesSettings :_*)
    .settings(
    CloudBees.applicationId := Some("cp2sca1a")
  )

}
