package server.service

import org.mockito.Mockito.verify
import org.scalatest.{BeforeAndAfter, FlatSpec}
import org.scalatest.mockito.MockitoSugar
import server.engine.ProgramController

/**
  * Created by dudek on 6/16/17.
  */
class ServerServiceTest extends FlatSpec with BeforeAndAfter with MockitoSugar {
  var programController: ProgramController = _
  var serverService: ServerService = _

  before {
    programController = mock[ProgramController]
    serverService = new ServerService(programController)
  }

  "Calling compile request" should "call the compile method properly" in {
    serverService.handleRequest("compile_request:program:abc:abc")

    verify(programController).setOutputJarName("program")
    verify(programController).prepareSharedProgram("abc:abc")
  }

  "Calling execute request" should "call the execute method properly" in {
    serverService.handleRequest("execute_request:abc")

    verify(programController).runJarFile("abc")
  }

  "Calling report request" should "call the report method properly" in {
    serverService.handleRequest("report_request:abc")

    verify(programController).makeDiffFromLatestFiles()
  }

}
