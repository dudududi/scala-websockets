package server

import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.Flow
import common.Protocol


object ServerService {
  def route: Route = path(Protocol.CONNECT_PATH) {
    println ("New client connected!")
    get {
      handleWebSocketMessages(connectService)
    }
  }

  val connectService: Flow[Message, Message, _] = Flow[Message].collect {
    case TextMessage.Strict(req) => handleRequest(req)
    case _ => TextMessage("Error! Message type unsupported")
  }

  def handleRequest(msg: String): TextMessage = {
    msg match {
      case s if s.startsWith(Protocol.COMPILE_REQUEST) => compile(s.stripPrefix(Protocol.COMPILE_REQUEST))
      case _ => TextMessage("Error! Unsupported command")
    }
  }

  private def compile(code: String): TextMessage = {

    TextMessage(Protocol.COMPILE_RESULT)
  }

}
