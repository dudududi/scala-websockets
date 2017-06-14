package client

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import client.service.ClientService

import scala.io.StdIn


/**
  * Created by sfurman on 14.06.17.
  */
object Client {

  object Config {
    val config = ConfigFactory.defaultApplication()
    val port = config.getInt("server.port")
    val host = config.getString("server.host")
  }

  def main(args: Array[String]) {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val serverService = new ClientService()
    val bindingFuture = Http().bindAndHandle(serverService.websocketRoute, Config.host, Config.port)
    println(s"Client server online at ${Config.host}:${Config.port}\nPress RETURN to stop...")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }

}
