package client.service

import java.net.URI

import akka.actor.{Actor, ActorSystem, Props}
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_17
import org.java_websocket.handshake.ServerHandshake


object ClientService {
  def apply(url: String)(implicit actorSystem: ActorSystem): ClientService = new ClientService(url, actorSystem)
}

class ClientService(url: String, actorSystem: ActorSystem) extends WebSocketClient(new URI(url), new Draft_17()) {

  override def onOpen(handshakeData: ServerHandshake): Unit = println("Web socket opened!")

  override def onMessage(message: String): Unit = println(message)

  override def onClose(code: Int, reason: String, remote: Boolean): Unit = println("Web socket closed")

  override def onError(ex: Exception): Unit = println(s"ERROR!! $ex")

  def sendRemoteExecuteRequest(message: String): Unit = {
    val talkActor = actorSystem.actorOf(props)

    println(s"Sending message: $message to server...")
    talkActor ! message
  }

  private val props = Props(new Actor {
    override def receive: Receive = {
      case msg: String =>
        println(s"Received message: $msg")
    }
  })

}
