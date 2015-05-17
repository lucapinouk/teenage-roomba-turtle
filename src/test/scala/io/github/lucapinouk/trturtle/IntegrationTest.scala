package io.github.lucapinouk.trturtle

import org.junit.Test
import org.scalatest.Matchers
import org.scalatest.junit.JUnitSuite

class IntegrationTest extends JUnitSuite with Matchers with Example {
  def checkMove(roomStatus: RoomStatus, direction: Direction, expectedHoover: Hoover): RoomStatus ={
    val newStatus = roomStatus.moveHoover(direction)
    newStatus.hoover should be (expectedHoover)

    newStatus
  }
  def checkCleaning(roomStatus: RoomStatus, direction: Direction, expectedCleaned: Int) ={
    val newStatus = roomStatus.moveHoover(direction)
    newStatus.hoover.cleanedSpots should be (expectedCleaned)
    newStatus.dirtSpots should be (Set((1,1)))
  }


  @Test
  def testExampleHooverMove() = {
    var status = checkMove(inputRoomStatus, North(), HooverImpl((1,3),0))
    status = checkMove(status, North(), HooverImpl((1,4),0))
    status = checkMove(status, East(), HooverImpl((2,4),0))
    status = checkMove(status, South(), HooverImpl((2,3),1))
    status = checkMove(status, East(), HooverImpl((3,3),1))
    status = checkMove(status, East(), HooverImpl((4,3),1))
    status = checkMove(status, South(), HooverImpl((4,2),1))
    status = checkMove(status, West(), HooverImpl((3,2),1))
    status = checkMove(status, North(), HooverImpl((3,3),1))
    status = checkMove(status, West(), HooverImpl((2,3),1))
    status = checkMove(status, West(), HooverImpl((1,3),1))
  }

  @Test
  def testBoundariesHooverMove(): Unit = {
    RoomStatus((1,5), Set(), HooverImpl((0,4), 0)).moveHoover(North()).hoover should be (HooverImpl((0,4), 0))
    RoomStatus((5,1), Set(), HooverImpl((4,0), 30)).moveHoover(East()).hoover should be (HooverImpl((4,0), 30))
    RoomStatus((5,15), Set(), HooverImpl((0,0), 30)).moveHoover(South()).hoover should be (HooverImpl((0,0), 30))
    RoomStatus((15,5), Set(), HooverImpl((0,0), 0)).moveHoover(West()).hoover should be (HooverImpl((0,0), 0))
  }

  @Test
  def testCleaningHooverMove(): Unit = {
    checkCleaning(RoomStatus((5,5), Set((1,1)), HooverImpl((2,2), 0)), North(), 0)
    checkCleaning(RoomStatus((5,5), Set((1,1),(2,3)), HooverImpl((2,2), 0)), North(), 1)
    checkCleaning(RoomStatus((5,5), Set((1,1)), HooverImpl((2,2), 1)), East(), 1)
    checkCleaning(RoomStatus((5,5), Set((1,1),(3,2)), HooverImpl((2,2), 1)), East(), 2)
    checkCleaning(RoomStatus((5,5), Set((1,1)), HooverImpl((2,2), 2)), South(), 2)
    checkCleaning(RoomStatus((5,5), Set((1,1),(2,1)), HooverImpl((2,2), 2)), South(), 3)
    checkCleaning(RoomStatus((5,5), Set((1,1)), HooverImpl((2,2), 3)), West(), 3)
    checkCleaning(RoomStatus((5,5), Set((1,1),(1,2)), HooverImpl((2,2), 3)), West(), 4)
  }

  @Test
  def testExampleMoveHooverObjects() = {
    inputRoomStatus.moveHoover(objectDirections) should be (outputRoomStatus)
  }

}
