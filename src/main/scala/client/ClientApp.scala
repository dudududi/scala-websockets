package client

import akka.actor.ActorSystem
import client.service.ClientService

object ClientApp extends App{
  implicit val actorSystem = ActorSystem()

  val client = ClientService("ws://localhost:9797/echo")

  if (client.connectBlocking()){
    client.sendRemoteExecuteRequest("requeeest")
  }


}
