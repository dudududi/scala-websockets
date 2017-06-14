name := "websockets"

version := "1.0"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-stream_2.12" % "2.5.2",
  "com.typesafe.akka" % "akka-stream-testkit_2.12" % "2.5.2" % "test",
  "com.typesafe.akka" % "akka-http-core_2.12" % "10.0.7",
  "com.typesafe.akka" % "akka-http-testkit_2.12" % "10.0.7" % "test",
  "com.typesafe.akka" % "akka-http-spray-json_2.12" % "10.0.7",
  "org.scalatest" % "scalatest_2.12" % "3.2.0-SNAP6" % "test"
)
