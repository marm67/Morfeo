package main.scala

case class Recurso(tipo: String, nombre: String)

trait ComandoParser
case class CemtParser(accion: String, recurso: Recurso, parametros: Map[String, String]) extends ComandoParser {
	def toComando = {
		"%s %s %s(%s) %s" format (
			 "CEMT" 
			, accion 
			, recurso.tipo 
			, recurso.nombre
			, parametros map { case (k,v) => "%s(%s)" format (k,v) } mkString ("", " ", " ")
		)
	}
}
case class CedaParser(accion: String, recurso: Recurso, parametros: Map[String, String]) extends ComandoParser
case class SetParser(variable: String, valor: String) extends ComandoParser

case class Target()
trait Comando
case class Cemt(accion: String, recurso: Recurso, parametros: Map[String, String], target: Target) extends Comando
case class Ceda(accion: String, recurso: Recurso, grupo: String, parametros: Map[String, String], target: Target) extends Comando
case class Set(variable: String, valor: String) extends ComandoParser

