package main.scala

import scala.io.Source

object Main {
  
  def main(args: Array[String]) {
    run(args(0))
  }
  
  def run(s: String) = {
    val path = s"""c:/scala/proyectos/morfeo/resources/scripts/$s"""
    val script = Source.fromFile(path).mkString.toUpperCase
    println(script.mkString)
    
    val p = new MorfeoParser
    val query = p.parseUno(script)
     
  }
}