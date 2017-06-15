package server

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object ServerService {
  def route: Route = pathEndOrSingleSlash {
    println ("New client request...")
    complete("Welcome to webSocket server")
  }
}
