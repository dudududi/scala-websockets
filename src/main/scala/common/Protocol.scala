package common

/**
  * Created by dudek on 6/15/17.
  */
object Protocol {
  val CONNECT_PATH = "connect"

  val COMPILE_REQUEST = "compile_request:"
  val COMPILE_RESULT_SUCCESS = "compile_result:success:"
  val COMPILE_RESULT_FAIL = "compile_result:fail"

  val EXECUTE_REQUEST = "execute_request:"
  val EXECUTE_RESULT = "execute_result:"

  val REPORT_REQUEST = "report_request"
  val REPORT_RESULT = "report_result:"

}
