import client.Client

object Main {
  def main(args: Array[String]) {
      val firstProgramClient = new Client()
      firstProgramClient.createProgramReceiver("one")

    //TODO za szybkie wykonanie nie pozwoli dokonczyc budowania plikow i jara
    // - podmienia sie nazwa pliku a poprzedni program nie nadaza sie budowac

    //      val secondProgramClient = new Client()
  //    secondProgramClient.createProgramReceiver("helloWorld")
//
//      val thirdProgramClient = new Client()
//      thirdProgramClient.createProgramReceiver("sum")

  }
}
