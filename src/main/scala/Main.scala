import client.Client

object Main {
  def main(args: Array[String]) {
      val firstProgramClient = new Client()
      firstProgramClient.createProgramReceiver("one")

//      val secondProgramClient = new Client()
//      secondProgramClient.createProgramReceiver("helloWorld")
//
//      val thirdProgramClient = new Client()
//      thirdProgramClient.createProgramReceiver("sum")

  }
}
