package io.github.lucapinouk.trturtle.io

import io.github.lucapinouk.trturtle._
import org.junit.Test
import org.scalatest.Matchers
import org.scalatest.junit.JUnitSuite
import scala.io.Source

class InputReaderTest extends JUnitSuite with Matchers with Example {

  @Test
  def testCorrectReadDirtSpots() = {
    InputReader.readDirtSpots(listDirtSpots.iterator, (6,6)) should be (objectDirtSpots, stringMoves)
    InputReader.readDirtSpots(("1 1"::"something"::Nil).iterator, (5,5)) should be (Set((1,1)), "something")
  }

  @Test
  def testWrongReadDirtSpots() = {
    InputReader.readDirtSpots(listDirtSpots.iterator, (1,1)) should be (Set(), "1 0")
  }


  @Test
  def testExampleRead() = {
    val(exampleRoomStatus, directions) = InputReader.read(Source.fromString(stringInput))

    exampleRoomStatus should be(inputRoomStatus)
    directions should be(objectDirections)
  }

  @Test
  def testMinimalRead() = {
    val(minimalRoomStatus, directions) = InputReader.read(Source.fromString("5 5\n1 2\nN"))

    minimalRoomStatus should be (RoomStatus((5, 5), Set(), HooverImpl((1, 2), 0)))
    directions should be (North()::Nil)
  }

  @Test
  def testNotEnoughInputRead() = {
    an [ParsingException] should be thrownBy {
      InputReader.read(Source.fromString(""))
    }
    an [ParsingException] should be thrownBy {
      InputReader.read(Source.fromString("5 5"))
    }
    an [ParsingException] should be thrownBy {
      InputReader.read(Source.fromString("5 5\n1 2"))
    }
  }

  @Test
  def testWrongRead() = {
    an [ParsingException] should be thrownBy {
      InputReader.read(Source.fromString("x y\n1 2\nN"))
    }
    an [ParsingException] should be thrownBy {
      InputReader.read(Source.fromString("5 5\n45 42\nN"))
    }
    an [ParsingException] should be thrownBy {
      InputReader.read(Source.fromString("5 5\n1 2\nSOMETHINGELSE"))
    }
  }

}
