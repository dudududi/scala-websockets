package server.engine
import java.io.PrintWriter
import scala.sys.process._

object ProgramController {

  var programOutputName: String = "program"

  def setOutputJarName (name: String): Unit = {
    programOutputName = name
  }

  def prepareSharedProgram(sourceCode: String): Unit = {
    val destinationPath: String = "./SharedProgram/src/" + programOutputName + ".scala"
    saveDownloadedSourceCodeToFile(destinationPath, sourceCode)
  }

  def saveDownloadedSourceCodeToFile(destinationPath: String, sourceCode: String): Boolean = {
    if (sourceCode != "" && sourceCode != "unknown_command") {
      val printer = new PrintWriter(destinationPath)
      printer.write(sourceCode)
      printer.close()
      buildJarFile()
      true
    } else {
      false
    }
  }

  def buildJarFile(): Boolean = {
    val consoleResult = Process(Seq("bash", "-c", "cd ./SharedProgram/ && bash ./build " + programOutputName )).!
    println("Compilation output: " + consoleResult)

    if (consoleResult == 0) {
      println("Program compiled successfully!")
      true
    } else {
      println("Compilation error!")
      false
    }
  }

  def runJarFile(jarName: String): Unit = {
    val consoleResult = "scala ./SharedProgram/" + jarName + ".jar".!!
    println("Jar program output: " + consoleResult.replace("\n", ""))
  }
}
