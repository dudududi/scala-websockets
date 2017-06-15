package client.service

import java.net.URI

import common.Protocol
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_17
import org.java_websocket.handshake.ServerHandshake


object ClientService {
  def apply(url: String, handler: ResultHandler): ClientService = new ClientService(url, handler)
}

class ClientService(url: String, handler: ResultHandler) extends WebSocketClient(new URI(url), new Draft_17()) {
  override def onOpen(handshakeData: ServerHandshake): Unit = println("Web socket opened!")

  override def onClose(code: Int, reason: String, remote: Boolean): Unit = println("Web socket closed")

  override def onError(ex: Exception): Unit = println(s"ERROR!! $ex")

  override def onMessage(message: String): Unit = {
    message match {
        case s if s.startsWith(Protocol.COMPILE_RESULT_SUCCESS) =>
          handler.onCompile(true)
          sendExecuteRequest(s.stripPrefix(Protocol.COMPILE_RESULT_SUCCESS))

        case Protocol.COMPILE_RESULT_FAIL =>
          handler.onCompile(false)

        case s if s.startsWith(Protocol.EXECUTE_RESULT) =>
          handler.onExecute(s.stripPrefix(Protocol.EXECUTE_RESULT))
          sendReportRequest()

        case s if s.startsWith(Protocol.REPORT_RESULT) =>
          handler.onReport(s.stripPrefix(Protocol.REPORT_RESULT))

        case _ => println("Unsupported response:  " + message)
      }
  }

  def sendRequest(code: String): Unit = {
    println("Send compile request to server...")
    send(s"${Protocol.COMPILE_REQUEST}$code")
  }

  private def sendExecuteRequest(programName: String): Unit = {
    println(s"Execute remotely program with name: $programName")
    send(s"${Protocol.EXECUTE_REQUEST}$programName")
  }

  private def sendReportRequest(): Unit ={
    println("Fetch diff report...")
    send(Protocol.REPORT_REQUEST)
  }

}
