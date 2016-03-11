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
    val presult = parseAll(script, s)
    presult match {
      case Success(r, n) => parseUnoOk(r)
      case Failure(msg, n) => println(presult)
      case Error(msg, n) => println(presult)
    }
  }

  def parseUnoOk(rs: List[ComandoParser]) = {
    println(rs)
    rs foreach { r => r match {
        case x: CemtParser => validaCemtParser(r.asInstanceOf[CemtParser])
        case x: CedaParser => validaCedaParser(r.asInstanceOf[CedaParser])
        case x: SetParser  => validaSetParser(r.asInstanceOf[SetParser])
      } 
    }
  }

  def validaCemtParser(c: CemtParser) = {
    println(c.toComando)

  }

  def validaCedaParser(c: CedaParser) = {
    println(c.parametros)

  }

  def validaSetParser(c: SetParser) = {
    println(c)

  }

}