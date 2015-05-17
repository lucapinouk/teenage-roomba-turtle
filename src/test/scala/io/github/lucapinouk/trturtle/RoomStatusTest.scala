package io.github.lucapinouk.trturtle

import org.junit.Test
import org.scalatest.Matchers
import org.scalatest.junit.JUnitSuite

class RoomStatusTest extends JUnitSuite with Matchers with Example {
  case class MockHoover(position: (Int, Int) = (1,1)) extends Hoover {
    override val cleanedSpots: Int = 999

    var storeCalls: List[((Int, Int), Set[(Int,Int)])] = Nil

    override def moveAndClean(newPosition: (Int, Int), dirtSpots: Set[(Int, Int)]): Hoover = {
      storeCalls = storeCalls :+ (newPosition, dirtSpots)
      this
    }
  }

  def checkMockHoover(hoover: Hoover, expectedCalls: List[((Int, Int), Set[(Int,Int)])]) = {
    hoover shouldBe a [MockHoover]
    hoover.asInstanceOf[MockHoover].storeCalls should be (expectedCalls)
  }

  def checkExampleMockMove(direction: Direction, expectedPosition: (Int, Int)) = {
    checkMockHoover(
      RoomStatus(objectRoomSize, objectDirtSpots, MockHoover())
        .moveHoover(direction).hoover,
        (expectedPosition, objectDirtSpots)::Nil)
  }
  @Test
  def testExampleMoveMockHoover() = {
    checkExampleMockMove(North(), (1,2))
    checkExampleMockMove(South(), (1,0))
    checkExampleMockMove(West(), (0,1))
    checkExampleMockMove(East(), (2,1))
  }

  def checkDirtSpotsMove(dirtSpots: Set[(Int,Int)], direction: Direction, expectedDirtSpots: Set[(Int,Int)]) = {
    RoomStatus((3, 3), Set(), MockHoover()).moveHoover(direction).dirtSpots should be (Set())
    RoomStatus((3, 3), dirtSpots, MockHoover()).moveHoover(direction).dirtSpots should be (expectedDirtSpots)
  }
  @Test
  def testDirtSpotsMoveHoover() = {
    checkDirtSpotsMove(full3x3Set, North(), full3x3Set - ((1,2)))
    checkDirtSpotsMove(full3x3Set, South(), full3x3Set - ((1,0)))
    checkDirtSpotsMove(full3x3Set, West(), full3x3Set - ((0,1)))
    checkDirtSpotsMove(full3x3Set, East(), full3x3Set - ((2,1)))
  }

  @Test
  def testBoundariesMockHooverDontMove(): Unit = {
    checkMockHoover(RoomStatus((1,5), Set(), MockHoover((0,4))).moveHoover(North()).hoover, Nil)
    checkMockHoover(RoomStatus((5,1), Set(), MockHoover((4,0))).moveHoover(East()).hoover, Nil)
    checkMockHoover(RoomStatus((5,15), Set(), MockHoover((0,0))).moveHoover(South()).hoover, Nil)
    checkMockHoover(RoomStatus((15,5), Set(), MockHoover((0,0))).moveHoover(West()).hoover, Nil)
  }

  @Test
  def testMultipleMoveHoover() = {
    val status = RoomStatus((3,3), full3x3Set, MockHoover())
      .moveHoover(North()::South()::West()::East()::Nil)

    checkMockHoover(status.hoover,
      ((1,2), full3x3Set):: //N
      ((1,0), full3x3Set - ((1,2))):: //S
      ((0,1), full3x3Set - ((1,2)) - ((1,0))):: //W
      ((2,1), full3x3Set - ((1,2)) - ((1,0)) - ((0,1))):: //E
      Nil)
  }

  @Test
  def testExampleMultipleMoveHoover() = {
    val status = RoomStatus(objectRoomSize, objectDirtSpots, MockHoover())
      .moveHoover(objectDirections)

    checkMockHoover(status.hoover,
      ((1,2), objectDirtSpots):: //N
      ((1,2), objectDirtSpots):: //N
      ((2,1), objectDirtSpots):: //E
      ((1,0), objectDirtSpots):: //S (removes dirt)
      ((2,1), objectDirtSpots - ((1,0))):: //E
      ((2,1), objectDirtSpots - ((1,0))):: //E
      ((1,0), objectDirtSpots - ((1,0))):: //S
      ((0,1), objectDirtSpots - ((1,0))):: //W
      ((1,2), objectDirtSpots - ((1,0))):: //N
      ((0,1), objectDirtSpots - ((1,0))):: //W
      ((0,1), objectDirtSpots - ((1,0))):: //W
      Nil)
  }

}

