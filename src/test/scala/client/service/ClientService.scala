package client.service

import org.scalatest.FlatSpec

class ClientService extends FlatSpec {
  "Calling send request" should "call in proper flow" in {
    val handler = new ResultHandler {
      override def onExecute(result: String): Unit = {

      }

      override def onReport(report: String): Unit = {

      }

      override def onCompile(boolean: Boolean): Unit = {

      }
    }
    val clientService = ClientService("ws:/localhost:8124/", handler)
    clientService.sendRequest("abc")
  }
}