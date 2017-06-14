package server.service

import akka.actor.ActorSystem
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Flow
import server.messages.ScalaProgramContainer

/**
  * Created by sfurman on 10.06.17.
  */
class ServerService() extends Directives {

  implicit val actorSystem = ActorSystem()
  implicit val actorMaterializer = ActorMaterializer()
  val scalaProgramContainer = ScalaProgramContainer

  val websocketRoute = get {
    handleWebSocketMessages(greeter)
  }

  def greeter: Flow[Message, Message, Any] =
    Flow[Message].collect {
      case TextMessage.Strict("one") ⇒
        println("Request for program one")
        TextMessage(scalaProgramContainer.programOne)

      case TextMessage.Strict("helloWord") =>
        println("Request for program hello word")
        TextMessage(scalaProgramContainer.helloWord)

      case TextMessage.Strict("sum") =>
        println("Request for program sum")
        TextMessage(scalaProgramContainer.sum)

      case TextMessage.Strict(txt) =>
        println(s"Recived unsupported message: $txt")
        TextMessage(s"Unsupported message")
    }
}
