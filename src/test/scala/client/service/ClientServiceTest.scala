package client.service

import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfter, FlatSpec}
import org.scalatest.mockito.MockitoSugar
class ClientServiceTest extends FlatSpec with MockitoSugar with BeforeAndAfter {
  var handler: ResultHandler = _
  var clientService: ClientService = _

  before {
    handler = mock[ResultHandler]
    clientService = spy(ClientService("ws:/localhost:8124", handler))

    doNothing().when(clientService).send(anyString())
  }

  "Calling send request" should "call the compile request properly" in {
    clientService.sendRequest("sample_name","abc")

    verify(clientService).send("compile_request:sample_name:abc")

  }

  "Calling onMessage with compile success" should "call the execute request properly" in {
    clientService.onMessage("compile_result:success:abc")

    verify(clientService).send("execute_request:abc")
    verify(handler).onCompile(true)
  }

  "Calling onMessage with compile failure" should "fall back with failure to handler" in {
    clientService.onMessage("compile_result:fail")

    verify(handler).onCompile(false)
  }

  "Calling onMessage with execute result" should "call the report request properly" in {
    clientService.onMessage("execute_result:abc:result")

    verify(clientService).send("report_request:abc")
    verify(handler).onExecute("result")
  }

  "Calling onMessage with report result" should "fall back to handler properly" in {
    clientService.onMessage("report_result:abc:report")

    verify(handler).onReport("report")
  }
}