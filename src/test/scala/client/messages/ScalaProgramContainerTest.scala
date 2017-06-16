package client.messages

import org.scalatest.FlatSpec

/**
 * Created by dudek on 6/16/17.
 */
class ScalaProgramContainerTest extends FlatSpec {

    "Method programOne" should "return proper name" in {
        val (name, _) = ScalaProgramContainer.programOne()
        assert(name == "programOne")
    }

    "Method helloWorld" should "return proper name" in {
        val (name, _) = ScalaProgramContainer.helloWord()
        assert(name == "helloWorld")
    }

    "Method sum" should "return proper name" in {
        val (name, _) = ScalaProgramContainer.sum()
        assert(name == "sum")
    }
}
