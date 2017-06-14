package client

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.ws.{Message, TextMessage, WebSocketRequest}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Keep, Sink, Source}
import com.typesafe.config.ConfigFactory

import scala.concurrent.Future

object Client {

    private val config = ConfigFactory.defaultApplication()
    private val serverAdress = config.getString("server.fullAddr")

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    private val webSocketFlow = Http().webSocketClientFlow(WebSocketRequest(s"ws://$serverAdress"))
    private val incoming: Sink[Message, Future[Done]] =
      Sink.foreach[Message] {
        case message: TextMessage.Strict =>
          println(message.text)
      }
    private val outgoing = Source.single(TextMessage("test1"))
    import system.dispatcher

    def createConnection(): Unit = {



      val (upgradeResponse, closed) =
      outgoing
        .viaMat(webSocketFlow)(Keep.right)
        .toMat(incoming)(Keep.both)
        .run()

      val connected = upgradeResponse.flatMap { upgrade =>
        if (upgrade.response.status == StatusCodes.SwitchingProtocols) {
          Future.successful(Done)
        } else {
          throw new RuntimeException(s"Connection failed: ${upgrade.response.status}")
        }
      }

      connected.onComplete(println)
    }

    def sendMessage (message: String): Unit = {

    }
}
