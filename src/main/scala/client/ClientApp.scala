package client

import akka.actor.ActorSystem
import client.messages.ScalaProgramContainer
import client.service.{ClientService, ResultHandler}
import common.Protocol

object ClientApp extends App with ResultHandler{
  implicit val actorSystem = ActorSystem()
  val url = "ws://localhost:9797"

  val client = ClientService(s"$url/${Protocol.CONNECT_PATH}", this)

  if (client.connectBlocking()){
    client.sendRequest(ScalaProgramContainer.helloWord)
  }

  override def onCompile(result: Boolean): Unit = {
    println( if(result) "Compilation succeeded" else "Compilation failed")
  }

  override def onExecute(result: String): Unit = {
    println (s"Result: $result")
  }

  override def onReport(report: String): Unit = {

  }
}
