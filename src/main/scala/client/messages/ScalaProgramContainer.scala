package client.messages

/**
  * Created by sfurman on 14.06.17.
  */
object ScalaProgramContainer {

  val programOne = "programOne" -> ("object program { \n" +
    "def main(args: Array[String]) { \n" +
    "val okResponseCode = 100 \n" +
    "println(okResponseCode) \n" +
    "} \n" +
    "}")

  val helloWord = "helloWorld" -> ("object program {\n" +
    "def main(args: Array[String]) {\n" +
    "println(\"Hello, world!\")\n" +
    "}\n" +
    "}")

  val sum = "sum" -> ("object program {\n" +
    "def main(args: Array[String]) {\n" +
    "val a = 5\n" +
    "val b = 6\n" +
    "println(a+b)\n" +
    "}\n" +
    "}")
}
