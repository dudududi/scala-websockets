package server.engine

import scala.sys.process._
import org.scalatest.FlatSpec

class ProgramControllerSpec extends FlatSpec {
  "Setting shared program name" should "return set program name" in {
    val programName = "test"
    val result = ProgramController.setOutputJarName(programName)
    assert(result == programName)
  }

  "Prepared shared program" should "return true if shared program directory was crated properly" in {
    val sourceCode = "object test { \n" +
      "def main(args: Array[String]) { \n" +
      "val okResponseCode = 100 \n" +
      "println(okResponseCode) \n" +
      "} \n" +
      "}"

    val result = ProgramController.prepareSharedProgram(sourceCode)
    assert(result)
  }

  "Run jar file" should "return program output if program running properly" in {
    val jarFileName = "test"
    val knownResult = "100"

    val result = ProgramController.runJarFile(jarFileName)
    assert(result.equals(knownResult))
    "rm -rf ./App_Data/Programs/test".!
  }

  "Saving downloaded source code" should "return false if sourceCode string is empty or equals 'unknown_command'" in {
    val destinationPath = "./App_Data/Programs/"
    var sourceCodeString = ""
    val resultWhileEmptyString = ProgramController.saveDownloadedSourceCodeToFile(destinationPath, sourceCodeString)
    sourceCodeString = "unknown_command"
    val resultWhileUnknownCommand = ProgramController.saveDownloadedSourceCodeToFile(destinationPath, sourceCodeString)

    assert(!resultWhileEmptyString && !resultWhileUnknownCommand)
  }

  "Building jar" should "return false if file or directory with name = programName not exist" in {
    val programName = "xxx"

    val result = ProgramController.buildJarFile(programName)
    assert(!result)
  }

  "Prepare difference raport" should "return '' if programName not exist or no differences" in {
    val programName = "test"

    val result = ProgramController.makeDiffFromLatestFiles(programName)

    assert(result.equals(""))

    "rm -rf ./App_Data/history".!
  }
}
