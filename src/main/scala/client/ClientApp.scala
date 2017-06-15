package client

import akka.actor.ActorSystem
import client.messages.ScalaProgramContainer
import client.service.ClientService
import common.Protocol

object ClientApp extends App{
  implicit val actorSystem = ActorSystem()
  val url = "ws://localhost:9797"

  val client = ClientService(s"$url/${Protocol.CONNECT_PATH}")

  if (client.connectBlocking()){
    client.sendCompileRequest(ScalaProgramContainer.helloWord, compileResultHandler)
  }


  def compileResultHandler(result: String): Unit ={
    println( "RESULT: " + result)
  }

}
