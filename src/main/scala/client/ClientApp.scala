package client

import akka.actor.ActorSystem
import client.service.ClientService

object ClientApp extends App{
  implicit val actorSystem = ActorSystem()

  val client = ClientService("http://localhost:9797/abc")

  if (client.connectBlocking()){
    client.sendRemoteExecuteRequest("requeeest")
  }


}
