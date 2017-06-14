import org.scalatest.FlatSpec
import Main._

class MainSpec extends FlatSpec {
  "saving downloaded source code to file" should "return false if source code is empty" in {
    val result = saveDownloadedSourceCodeToFile("./test.scala","")
    assert(!result)
  }
}
