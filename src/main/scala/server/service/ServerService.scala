package server.service

import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.Flow
import common.Protocol
import server.engine.ProgramController

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
      case s if s.startsWith(Protocol.EXECUTE_REQUEST) => execute(s.stripPrefix(Protocol.EXECUTE_REQUEST))
      case Protocol.REPORT_REQUEST => report()
      case _ => TextMessage("Error! Unsupported command")
    }
  }

  private def compile(code: String): TextMessage = {
    //val name = s"clientProgram-${System.currentTimeMillis()}"
    val name = "program"
    println(s"Compiling received program, name: $name")

    ProgramController.setOutputJarName(name)
    val result = ProgramController.prepareSharedProgram(code)
    TextMessage(if (result) s"${Protocol.COMPILE_RESULT_SUCCESS}$name" else Protocol.COMPILE_RESULT_FAIL)
  }

  private def execute(name: String): TextMessage = {
    println(s"Executing program with name: $name")
    val result = ProgramController.runJarFile(name)
    TextMessage(s"${Protocol.EXECUTE_RESULT}$result")
  }

  private def report(): TextMessage = {
    println("Creating diff report")
    val result = ProgramController.makeDiffFromLatestFiles()
    TextMessage(s"${Protocol.REPORT_RESULT}$result")
  }

}
