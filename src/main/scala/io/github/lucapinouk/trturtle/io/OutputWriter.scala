package io.github.lucapinouk.trturtle.io

import io.github.lucapinouk.trturtle.Hoover

/* Writes the outputs of the program */
object OutputWriter {
  /* Writes the status of the Hoover in a string */
  def write(hoover: Hoover): String = s"${hoover.position._1} ${hoover.position._2}\n${hoover.cleanedSpots}"
}
