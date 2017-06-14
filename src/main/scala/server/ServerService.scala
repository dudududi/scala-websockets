package server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives
import akka.stream.scaladsl.Flow

import scala.io.StdIn

/**
  * Created by sfurman on 10.06.17.
  */
class ServerService() extends Directives {

  implicit val actorSystem = ActorSystem()
  implicit val actorMaterializer = ActorMaterializer()

  val websocketRoute = get {
    handleWebSocketMessages(greeter)
  }

  def greeter: Flow[Message, Message, Any] =
    Flow[Message].collect {
      case TextMessage.Strict(txt) ⇒ TextMessage(txt)
    }
}