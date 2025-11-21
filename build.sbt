ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.20"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.5.6",
  "org.apache.spark" %% "spark-sql" % "3.5.6",
  "org.questdb" % "questdb" % "9.2.0",

  "com.holdenkarau" %% "spark-testing-base" % "3.5.6_2.1.3" % Test
)

lazy val root = (project in file("."))
  .settings(
    name := "SparkQuest",
    idePackagePrefix := Some("org.github.helkaroui.sparkquest")
  )
