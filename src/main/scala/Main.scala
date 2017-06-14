import server.Server

object Main {
  def main(args: Array[String]) {
      val firstProgramClient = new Server()
      firstProgramClient.createProgramReceiver("one")

//      val secondProgramClient = new Client()
//      secondProgramClient.createProgramReceiver("helloWorld")
//
//      val thirdProgramClient = new Client()
//      thirdProgramClient.createProgramReceiver("sum")

  }
}
