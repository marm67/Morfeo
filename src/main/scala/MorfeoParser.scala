package main.scala

import scala.util.parsing.combinator._
import scala.util.parsing.combinator.syntactical._

class MorfeoParser extends JavaTokenParsers {
  
  def script: Parser[List[ComandoParser]] = comando ~ rep(comando) ^^
    { case a ~ b => a :: b }

  def comando: Parser[ComandoParser] = cemt | ceda | set

  def cemt: Parser[ComandoParser] = "CEMT" ~ accionCemt ~ recurso ~ parametros ^^
    { case a ~ b ~ c ~ d => CemtParser(b, c, d) }

  def ceda: Parser[ComandoParser] = "CEDA" ~> accionCeda ~ recurso ~ parametros ^^
    { case a ~ b ~ c => CedaParser(a,b,c) }

  def set: Parser[ComandoParser] = "SET" ~> ident ~ ("(" ~> ident <~ ")") ^^
    { case a ~ b => SetParser(a, b) }

  def accionCemt: Parser[String] = "INQUIRE" | "CREATE" | "SET" | "DISCARD"

  def accionCeda: Parser[String] = "EXPAND" | "DEFINE" | "ALTER" | "DELETE"

  def recurso: Parser[Recurso] = ident ~ "(" ~ valor ~ ")" ^^
    { case k ~ "(" ~ v ~ ")" => Recurso(k, v) }

  def parametros: Parser[Map[String, String]] = rep(parametro) ^^ { _.toMap }

  def parametro: Parser[(String, String)] = ident ~ "(" ~ valor ~ ")" ^^
    { case k ~ "(" ~ v ~ ")" => k -> v }

  def valor: Parser[String] = """(\w|\*)+""".r
  
  def parseUno(s: String) = {
    println(parseAll(script, s))
  }

}