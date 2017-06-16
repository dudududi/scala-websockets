package client.messages

/**
  * Created by sfurman on 14.06.17.
  */
object ScalaProgramContainer {

  def programOne(): (String, String) = {
    ("programOne",
      """object one {
          def main(args: Array[String]) {
            val okResponseCode = 100
            println(okResponseCode)
          }
      }""")
  }

  def helloWord(): (String, String) = {
    ("helloWorld",
      """object helloWorld {
          def main(args: Array[String]) {
            println("Hello, world!")
          }
         }""")
  }

  def sum(): (String, String) = {
    ("sum",
      """object sum {
          def main(args: Array[String]) {
            val a = 5
            val b = 6
            println(a+b)
          }
         }""")
  }
}
