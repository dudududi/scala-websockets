package server.service

import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.Flow
import common.Protocol
import server.engine.ProgramController

class ServerService(programController: ProgramController) {
  def route: Route = path(Protocol.CONNECT_PATH) {
    println("New client connected!")
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
      case s if s.startsWith(Protocol.REPORT_REQUEST) => report(s.stripPrefix(Protocol.REPORT_REQUEST))
      case _ => TextMessage("Error! Unsupported command")
    }
  }

  private def compile(msg: String): TextMessage = {
    val name = msg.split(":").head
    val code = msg.stripPrefix(s"$name:")

    println(s"Compiling received program, name: $name")

    programController.setOutputJarName(name)
    var result = false
    try {
      result = programController.prepareSharedProgram(code)
    } catch {
      case e: Exception => result = false
    }

    TextMessage(if (result) s"${Protocol.COMPILE_RESULT_SUCCESS}$name" else Protocol.COMPILE_RESULT_FAIL)
  }

  private def execute(name: String): TextMessage = {
    println(s"Executing program with name: $name")
    var result = ""
    try {
      result = programController.runJarFile(name)
    } catch {
      case e: Exception => result = "Running error!"
    }

    TextMessage(s"${Protocol.EXECUTE_RESULT}$name:$result")
  }

  private def report(name: String): TextMessage = {
    println(s"Creating diff report for program: $name")
    val result = programController.makeDiffFromLatestFiles(name)
    TextMessage(s"${Protocol.REPORT_RESULT}$name:$result")
  }

}
