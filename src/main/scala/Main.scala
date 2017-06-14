import java.io.PrintWriter

import client.Client

import scala.sys.process._

object Main {
  def main(args: Array[String]) {

    val properlyFileResult: Int = 100
    val sourceCode: String = downloadSharedProgramFromServer()

//    prepareSharedProgram(sourceCode)
//    buildJarFile(properlyFileResult)
//    runJarFile()

  }

  def prepareSharedProgram(sourceCode:String): Unit = {
    val destinationPath: String = "./SharedProgram/src/SharedProgram.scala"

    saveDownloadedSourceCodeToFile(destinationPath, sourceCode)
  }

  def saveDownloadedSourceCodeToFile(destinationPath: String, sourceCode: String): Boolean = {
      if(sourceCode != "") {
        val printer = new PrintWriter(destinationPath)
        printer.write(sourceCode)
        printer.close()
        true
      } else {
         false
      }
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
    Client.createConnection()
    Client.sendMessage("test2")

    //TODO to implement - websocket connection
    new String("object SharedProgram { \n def main(args: Array[String]) { \n val okResponseCode = 100 \n println(okResponseCode) \n} \n}\n")
  }

  def sendCompiledProgramToClient(): Unit ={
    //TODO to implement - websocket connection
  }
}
