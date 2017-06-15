package server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import engine.ProgramController

import scala.io.StdIn



object Server extends App{

  implicit val actorSystem = ActorSystem()
  implicit val flowMaterializer = ActorMaterializer()
  implicit val executionContext = actorSystem.dispatcher

  private val config = actorSystem.settings.config
  private val serverHost = config.getString("server.host")
  private val serverPort = config.getInt("server.port")

  private val route = ServerService.route

  val binding = Http().bindAndHandle(route, serverHost, serverPort)

  println (s"Server is running at http://$serverHost:$serverPort ...")

  StdIn.readLine()

  binding.flatMap(_.unbind()).onComplete(_ => actorSystem.terminate())
  println("Server is down.")

}
