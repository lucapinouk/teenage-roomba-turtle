package io.github.lucapinouk.trturtle

import io.{ParsingException, InputReader, OutputWriter}
import scala.io.Source

/* Exception used to report the wrong number of arguments */
case class WrongArgumentsNumberException() extends Exception()

/* Executable program Teenage Roomba Turtle */
object TRTurtle extends App {
  new TRTurtle(args).execute()
}

/* Teenage Roomba Turtle program behaviour */
class TRTurtle(args: Array[String]) extends Terminal {
  def execute() {
    try {
      val filename = args.length match {
        case 0 => "input.txt"
        case 1 => args(0)
        case _ => throw new WrongArgumentsNumberException()
      }

      /* test file exists and is readable, if not fail */
      val file = new java.io.File(filename)
      if (!file.exists || !file.canRead) {
        failWithMsg("Unable to access file " + filename)
      }

      val source = Source.fromFile(filename)

      val (initRoomStatus, directions) = InputReader.read(source)
      val finalHoover = initRoomStatus.moveHoover(directions).hoover
      printMsg(OutputWriter.write(finalHoover))

    } catch {
      case ParsingException(msg) => failWithMsg("PARSING ERROR: " + msg)

      case WrongArgumentsNumberException() =>
        failWithMsg("Unexpected number of arguments. Only a path for the input file is accepted.")

      case _: Throwable => failWithMsg("Unexpected error")
    }
  }

}

/* Trait defining where to print and what to do in case of a failure */
trait Terminal {
  def printMsg(msg: String) = println(msg)

  def failWithMsg(msg: String): Unit = {
    Console.err.println(msg)
    System.exit(1)
  }
}
