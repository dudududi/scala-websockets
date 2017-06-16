package server.engine

import scala.sys.process._
import org.scalatest.{BeforeAndAfter, FlatSpec}

class ProgramControllerSpec extends FlatSpec with BeforeAndAfter{
  var programController: ProgramController = _

  before {
    programController = new ProgramController
  }

  "Setting shared program name" should "return set program name" in {
    val programName = "program"
    val result = programController.setOutputJarName(programName)
    assert(result == programName)
  }

  "Prepared shared program" should "return true if shared program directory was crated properly" in {
    val sourceCode = "object program { \n" +
      "def main(args: Array[String]) { \n" +
      "val okResponseCode = 100 \n" +
      "println(okResponseCode) \n" +
      "} \n" +
      "}"

    val result = programController.prepareSharedProgram(sourceCode)
    assert(result)
  }

  "Run jar file" should "return program output if program running properly" in {
    val jarFileName = "program"
    val knownResult = "100"

    val result = programController.runJarFile(jarFileName)
    assert(result.equals(knownResult))
    "rm -rf ./App_Data/Programs/program".!
  }

  "Saving downloaded source code" should "return false if sourceCode string is empty or equals 'unknown_command'" in {
    val destinationPath = "./App_Data/Programs/"
    var sourceCodeString = ""
    val resultWhileEmptyString = programController.saveDownloadedSourceCodeToFile(destinationPath, sourceCodeString)
    sourceCodeString = "unknown_command"
    val resultWhileUnknownCommand = programController.saveDownloadedSourceCodeToFile(destinationPath, sourceCodeString)

    assert(!resultWhileEmptyString && !resultWhileUnknownCommand)
  }

  "Building jar" should "return false if file or directory with name = programName not exist" in {
    val programName = "xxx"

    val result = programController.buildJarFile(programName)
    assert(!result)
  }

  "Prepare difference raport" should "return '' if programName not exist or no differences" in {
    val result = programController.makeDiffFromLatestFiles()
    assert(result.equals(""))
  }
}
