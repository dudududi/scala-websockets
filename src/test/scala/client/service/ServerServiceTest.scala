package client.service

import akka.http.scaladsl.testkit.{ScalatestRouteTest, WSProbe}
import org.scalatest.{FunSuite, Matchers}
import client.messages.ScalaProgramContainer

/**
  * Created by sfurman on 11.06.17.
  */
class ServerServiceTest extends FunSuite with Matchers with ScalatestRouteTest{

  val UNSUPPORTED_MSG = "Unsupported message"

  test("should create empty server"){
    new ClientService()
  }

  test("should be able to connect to the Server"){
    val wsClient = WSProbe()
    val serverService = new ClientService()

    // WS creates a WebSocket request for testing
    WS("/", wsClient.flow) ~> serverService.websocketRoute ~>
      check {
        // check response for WS Upgrade headers
        isWebSocketUpgrade shouldEqual true
      }
  }

  test("should respond with program one"){
    val serverService = new ClientService()
    val wsClient = WSProbe()
    val respone = ScalaProgramContainer.programOne

    WS("/", wsClient.flow) ~> serverService.websocketRoute ~>
      check {
        wsClient.sendMessage("one")
        wsClient.expectMessage(respone)
      }
  }

  test("should respond with program hello word"){
    val serverService = new ClientService()
    val wsClient = WSProbe()
    val respone = ScalaProgramContainer.helloWord

    WS("/", wsClient.flow) ~> serverService.websocketRoute ~>
      check {
        wsClient.sendMessage("helloWord")
        wsClient.expectMessage(respone)
      }
  }

  test("should respond with program sum"){
    val serverService = new ClientService()
    val wsClient = WSProbe()
    val respone = ScalaProgramContainer.sum

    WS("/", wsClient.flow) ~> serverService.websocketRoute ~>
      check {
        wsClient.sendMessage("sum")
        wsClient.expectMessage(respone)
      }
  }

  test("should respond with unsupported msg"){
    val serverService = new ClientService()
    val wsClient = WSProbe()
    val respone = ScalaProgramContainer.programOne

    WS("/", wsClient.flow) ~> serverService.websocketRoute ~>
      check {
        wsClient.sendMessage("different msg")
        wsClient.expectMessage(UNSUPPORTED_MSG)
      }
  }

}