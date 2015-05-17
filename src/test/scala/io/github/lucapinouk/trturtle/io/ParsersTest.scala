package io.github.lucapinouk.trturtle.io

import io.github.lucapinouk.trturtle._

import org.junit.Test
import org.scalatest.Matchers
import org.scalatest.junit.JUnitSuite

class ParsersTest extends JUnitSuite with Matchers with Example {
  val konamiCode = "NNSSWEWEBA"

  @Test
  def testCorrectParseCoord() = {
    CoordParser.parseCoord(stringRoomSize) shouldBe an [CoordParser.Success[_]]

    CoordParser.parseCoord(stringRoomSize).get should be (objectRoomSize)
    CoordParser.parseCoord(stringHooverPos).get should be (objectHooverPos)
    CoordParser.parseCoord("555 555").get should be ((555, 555))
  }

  @Test
  def testWrongParseCoord() = {
    CoordParser.parseCoord("x y") should not be an [CoordParser.Success[_]]
    CoordParser.parseCoord("x 5") should not be an [CoordParser.Success[_]]
    CoordParser.parseCoord("5 y") should not be an [CoordParser.Success[_]]
    CoordParser.parseCoord("5x 5") should not be an [CoordParser.Success[_]]
    CoordParser.parseCoord("x 5y") should not be an [CoordParser.Success[_]]
    CoordParser.parseCoord("5 5 5") should not be an [CoordParser.Success[_]]
  }

  @Test
  def testCorrectReadCoordInBoundary() = {
    CoordParser.parseCoordInBoundary("5 5", (6, 6)) shouldBe an [CoordParser.Success[_]]
    CoordParser.parseCoordInBoundary("5 5", (6, 6)).get should be((5, 5))
  }
  @Test
  def testWrongReadCoordInBoundary() = {
    CoordParser.parseCoordInBoundary("5 5", (1, 1)) should not be an [CoordParser.Success[_]]
    CoordParser.parseCoordInBoundary("5 5", (5, 6)) should not be an [CoordParser.Success[_]]
    CoordParser.parseCoordInBoundary("5 5", (6, 5)) should not be an [CoordParser.Success[_]]
  }

  @Test
  def testCorrectReadMoves() =
    DirectionParser.parseDirections(stringMoves) should be (objectDirections)

  @Test
  def testWrongReadMoves() = {
    an [ParsingException] should be thrownBy {
      DirectionParser.parseDirections(konamiCode)
    }
    an [ParsingException] should be thrownBy {
      DirectionParser.parseDirections("")
    }
  }
}
