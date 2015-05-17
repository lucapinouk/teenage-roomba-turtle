package io.github.lucapinouk.trturtle

import org.junit.Test
import org.scalatest.Matchers
import org.scalatest.junit.JUnitSuite

class HooverTest extends JUnitSuite with Matchers with Example {
  @Test
  def testCleanSpot(): Unit = {
    HooverImpl((2, 2), 0).cleanSpot(Set((1,1))).cleanedSpots should be (0)
    HooverImpl((2, 2), 0).cleanSpot(Set((1,1),(2,2))).cleanedSpots should be (1)
    HooverImpl((0, 0), 10).cleanSpot(full3x3Set - ((0,0))).cleanedSpots should be (10)
    HooverImpl((0, 0), 10).cleanSpot(full3x3Set).cleanedSpots should be (11)
  }


  def checkMoveAndClean(hoover: Hoover, newPosition: (Int, Int), dirtSpots: Set[(Int,Int)], expectedCleaned: Int) ={
    val newHoover = hoover.moveAndClean(newPosition, dirtSpots)

    newHoover.position should be (newPosition)
    newHoover.cleanedSpots should be (expectedCleaned)
  }

  @Test
  def testCleaningHooverMove(): Unit = {
    checkMoveAndClean(HooverImpl((1,1), 0), (1,2), full3x3Set - ((1,2)), 0)
    checkMoveAndClean(HooverImpl((1,1), 0), (1,2), full3x3Set, 1)
    checkMoveAndClean(HooverImpl((1,1), 1), (1,0), full3x3Set - ((1,0)), 1)
    checkMoveAndClean(HooverImpl((1,1), 1), (1,0), full3x3Set, 2)
    checkMoveAndClean(HooverImpl((1,1), 2), (2,1), full3x3Set - ((2,1)), 2)
    checkMoveAndClean(HooverImpl((1,1), 2), (2,1), full3x3Set, 3)
    checkMoveAndClean(HooverImpl((1,1), 3), (0,1), full3x3Set - ((0,1)), 3)
    checkMoveAndClean(HooverImpl((1,1), 3), (0,1), full3x3Set, 4)
  }
}
