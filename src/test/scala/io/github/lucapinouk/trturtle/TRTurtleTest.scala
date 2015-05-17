package io.github.lucapinouk.trturtle

import org.junit._
import org.scalatest.Matchers
import org.scalatest.junit.JUnitSuite

class TRTurtleTest extends JUnitSuite with Matchers with Example {
  class MockExit(msg: String) extends Exception(msg)

  trait MockTerminal extends Terminal {
    var outMessage: String = ""

    override def printMsg(msg: String) = outMessage = msg
    override def failWithMsg(msg: String) = throw new MockExit(msg)
  }

  def executeWithOutputs(args: Array[String], output: String, isError: Boolean = false): Unit = {
    val trturtle = new TRTurtle(args) with MockTerminal

    if(isError)
      a [MockExit] should be thrownBy {
        trturtle.execute()
      }
    else
      noException should be thrownBy {
        trturtle.execute()
      }

    trturtle.outMessage should be(output)
  }

  @Test
  def testExample() = {
    executeWithOutputs(Array(), stringOutput)
    executeWithOutputs(Array("input.txt"), stringOutput)
    executeWithOutputs(Array("examples/input.txt"), stringOutput)
    executeWithOutputs(Array("examples/circleInput.txt"), "0 0\n8")
    executeWithOutputs(Array("examples/cellInput.txt"), "0 0\n0")
  }

  @Test
  def testErrors() = {
    executeWithOutputs(Array("file-not-existent"), "", isError = true)
    executeWithOutputs(Array("examples/wrongInput.txt"), "", isError = true)
    executeWithOutputs(Array("input.txt", "otherArgs"), "", isError = true)
  }

}


