import java.io.PrintWriter
import sys.process._

object Main {
  def main(args: Array[String]) {

    val properlyFileResult: Int = 100
    prepareSharedProgram()
    buildJarFile(properlyFileResult)
    runJarFile()

  }

  def prepareSharedProgram(): Unit = {
    val sourceCode: String = downloadSharedProgramFromServer()
    val destinationPath: String = "./SharedProgram/src/SharedProgram.scala"

    saveDownloadedSourceCodeToFile(destinationPath, sourceCode)
  }

  def saveDownloadedSourceCodeToFile(destinationPath: String, sourceCode: String): Unit = {
    val printer = new PrintWriter(destinationPath)
    printer.write(sourceCode)
    printer.close()
  }

  def buildJarFile(properlyFileResult: Int): Unit = {
    val consoleResult = Process(Seq("bash","-c","cd ./SharedProgram/ && bash ./build")).!!
    println("Shared program output: " + consoleResult.replace("\n",""))

    if(Integer.parseInt(consoleResult.replace("\n","")) == properlyFileResult) {
      println("Program compiled successfully!")
    } else {
      println("Compilation error!")
    }
  }

  def runJarFile(): Unit = {
    val consoleResult = "scala ./SharedProgram/program.jar".!!
    println("Jar program output: " + consoleResult.replace("\n",""))
  }

  def downloadSharedProgramFromServer(): String ={
    //TODO to implement - websocket connection
    new String("object SharedProgram { \n def main(args: Array[String]) { \n val okResponseCode = 100 \n println(okResponseCode) \n} \n}\n")
  }

  def sendCompiledProgramToClient(): Unit ={
    //TODO to implement - websocket connection
  }
}
