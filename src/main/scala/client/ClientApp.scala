package client

import akka.actor.ActorSystem
import client.messages.ScalaProgramContainer
import client.service.{ClientService, ResultHandler}
import common.Protocol

object ClientApp extends App with ResultHandler{
  implicit val actorSystem = ActorSystem()
  val url = "ws://localhost:9797"

  var client = ClientService(s"$url/${Protocol.CONNECT_PATH}", this)

  if (client.connectBlocking()){
    val (name, code) = ScalaProgramContainer.helloWord()
    client.sendRequest(name, code)
  }

  override def onCompile(result: Boolean): Unit = {
    println( if(result) "Compilation succeeded" else "Compilation failed")
  }

  override def onExecute(result: String): Unit = {
    println (s"Result: $result")
  }

  override def onReport(report: String): Unit = {
    println (s"Report: $report")
  }
}
