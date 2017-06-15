package client.service

import java.net.URI

import common.Protocol
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_17
import org.java_websocket.handshake.ServerHandshake


object ClientService {
  def apply(url: String): ClientService = new ClientService(url)
}

class ClientService(url: String) extends WebSocketClient(new URI(url), new Draft_17()) {
  override def onOpen(handshakeData: ServerHandshake): Unit = println("Web socket opened!")

  override def onClose(code: Int, reason: String, remote: Boolean): Unit = println("Web socket closed")

  override def onError(ex: Exception): Unit = println(s"ERROR!! $ex")

  override def onMessage(message: String): Unit = {
    message match {
        case Protocol.COMPILE_RESULT => compileCallback(message)
        case _ => println("Unsupported response:  " + message)
      }
  }

  def sendCompileRequest(code: String, callback: String => Unit): Unit = {
    println("Sending compile request to server...")
    compileCallback = callback
    send(s"${Protocol.COMPILE_REQUEST}$code")
  }

  private var compileCallback: String => Unit =_
}
