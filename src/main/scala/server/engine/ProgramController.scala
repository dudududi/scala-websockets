package server.engine
import java.io.PrintWriter
import scala.sys.process._

class ProgramController {

  var programOutputName: String = "program"

  def setOutputJarName(name: String): String = {
    programOutputName = name
    programOutputName
  }

  def prepareSharedProgram(sourceCode: String): Boolean = {
    val consoleResult = ("mkdir -p ./App_Data/Programs/" + programOutputName + "/src/").!
    println("Creating shared program directory status: " + consoleResult)

    if (consoleResult == 0) {
      val destinationPath: String = "./App_Data/Programs/" + programOutputName + "/src/" + programOutputName + ".scala"
      saveDownloadedSourceCodeToFile(destinationPath, sourceCode)
    } else {
      false
    }
  }

  def saveDownloadedSourceCodeToFile(destinationPath: String, sourceCode: String): Boolean = {
    if (sourceCode != "" && sourceCode != "unknown_command") {
      val printer = new PrintWriter(destinationPath)
      printer.write(sourceCode)
      printer.close()
      buildJarFile(programOutputName)
    } else {
      false
    }
  }

  def buildJarFile(programName: String): Boolean = {
    val consoleResult = Process(Seq("bash", "-c", "cd ./App_Data/ && bash ./prepare_program_files " + programName)).!
    println("Compilation output: " + consoleResult)

    if (consoleResult == 0) {
      println("Program compiled successfully!")
      true
    } else {
      println("Compilation error!")
      false
    }
  }

  def runJarFile(jarName: String): String = {
    val consoleResult = ("scala ./App_Data/Programs/" + jarName + "/" + jarName + ".jar").!!
    println("Jar program output: " + consoleResult.replace("\n", ""))
    consoleResult.replace("\n", "")
  }

  def makeDiffFromLatestFiles(): String ={
    val consoleResult = Process(Seq("bash", "-c", "cd ./App_Data/ && bash ./prepare_difference_report " + programOutputName)).!!

    println(s"Report: $consoleResult")
    consoleResult
  }
}
