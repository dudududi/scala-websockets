package server.service

import org.mockito.Mockito._
import org.mockito.Matchers._
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

  "Calling compile request" should "call the compile method properly and succeed" in {
    when(programController.prepareSharedProgram(anyString())).thenReturn(true)

    val msg = serverService.handleRequest("compile_request:program:abc:abc")

    verify(programController).setOutputJarName("program")
    verify(programController).prepareSharedProgram("abc:abc")
    assert(msg.getStrictText == "compile_result:success:program")
  }

  "Calling compile request" should "call the compile method properly and fails" in {
    when(programController.prepareSharedProgram(anyString())).thenReturn(false)

    val msg = serverService.handleRequest("compile_request:program:abc:abc")

    verify(programController).setOutputJarName("program")
    verify(programController).prepareSharedProgram("abc:abc")
    assert(msg.getStrictText == "compile_result:fail")
  }

  "Calling execute request" should "call the execute method properly" in {
    when(programController.runJarFile(anyString())).thenReturn("sampleResult")

    val msg = serverService.handleRequest("execute_request:abc")

    verify(programController).runJarFile("abc")
    assert(msg.getStrictText == "execute_result:abc:sampleResult")
  }

  "Calling report request" should "call the report method properly" in {
    when(programController.makeDiffFromLatestFiles(anyString())).thenReturn("sampleReport")

    val msg = serverService.handleRequest("report_request:abc")

    verify(programController).makeDiffFromLatestFiles("abc")
    assert(msg.getStrictText == "report_result:abc:sampleReport")
  }

}
