package client.messages

/**
  * Created by sfurman on 14.06.17.
  */
object ScalaProgramContainer {

  def programOne(): (String, String) = {
    ("programOne",
      """object programOne {
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

  def creators(): (String, String) = {
    ("creators",
      """object creators {
          def main(args: Array[String]) {
            println("Grupa K. Dudek©\nW składzie:\nBartłomiej Dobosz\nSzymon Furmański\nKamil Dudek\nŁukasz Dudek\nWaldemar Bulanda\nAll rights resered 2017")
          }
      }""")
  }

  def bado() :(String, String) ={
    ("badoo",
      """object badoo {
          import scala.util.Random
          def main(args: Array[String]){
            val where = "Gdzie jest Badoo ? Maybe "
            var positions = Array("Zambia", "Madagascar", "New Zealand")
            for ( x <- positions ) {
              println( where + x )
            }
            println("Oh! I know !")
            println("Badoo is in "+Random.shuffle(positions.toList).head)
          }
      }""".stripMargin)
  }
}
