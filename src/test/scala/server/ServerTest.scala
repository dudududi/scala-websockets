package server

import akka.http.scaladsl.testkit.{ScalatestRouteTest, WSProbe}
import org.scalatest.{FunSuite, Matchers}

/**
  * Created by sfurman on 11.06.17.
  */
class ServerTest extends FunSuite with Matchers with ScalatestRouteTest{

  test("should create empty server"){
    new ServerService()
  }

  test("should be able to connect to the Server"){
    val wsClient = WSProbe()
    val serverService = new ServerService()

    // WS creates a WebSocket request for testing
    WS("/greeter", wsClient.flow) ~> serverService.websocketRoute ~>
      check {
        // check response for WS Upgrade headers
        isWebSocketUpgrade shouldEqual true
      }
  }

  test("should respond with correct message"){
    val serverService = new ServerService()
    val wsClient = WSProbe()

    WS("/greeter", wsClient.flow) ~> serverService.websocketRoute ~>
      check {
        wsClient.sendMessage("hello")
        wsClient.expectMessage("hello")
      }
  }

}


