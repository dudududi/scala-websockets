package client.service

/**
  * Created by dudek on 6/15/17.
  */
trait ResultHandler {
  def onCompile(boolean: Boolean)
  def onExecute(result: String)
  def onReport(report: String)
}
