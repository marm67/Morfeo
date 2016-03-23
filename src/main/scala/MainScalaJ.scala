object Main extends App {
	def hgc() {
		var url = "http://cpub.tesa/hgc/portal/calidad/index/"
    	val response = HTTPRequest(url)
      	println(response.mkString)
	}

	def cmci() {
		var url = "http://10.145.254.228:8632/CICSSystemManagement/CICSLocalTransaction/JPLX1/CICSJCOA//10?CRITERIA=(TRANID=XA*)"
    	val response = HTTPRequest(url, "_usuario", "_password")
      	println(response.mkString)
	}

	def xsd() {
		var url = "http://10.145.254.228:8632/CICSSystemManagement/schema/CICSSystemManagement.xsd"
    	val response = HTTPRequest(url, "_usuario", "_password")
      	println(response.mkString)
	}

	def scalajCmci() {
		var url = "http://10.145.254.228:8632/CICSSystemManagement/CICSLocalTransaction/JPLX1/CICSJCOA//10?CRITERIA=(TRANID=XA*)"
    	val response = ScalaJ(url, "_usuario", "_password")
      	//println(response.asParamMap)

  //   	println("---> response.body")
  //   	println(response.body)
  //   	println(" ")
		// println("---> response.code")
		// println(response.code)
		// println(" ")
		// println("---> response.headers")
		// println(response.headers)
		// println(" ")
		// println("---> response.cookies")
		// println(response.cookies)
		// println(" ")

      	// http://alvinalexander.com/scala/how-to-extract-data-from-xml-nodes-in-scala
      	// http://www.scala-lang.org/api/current/scala-xml/
      	
      	val body = scala.xml.XML.loadString(response.asString.body)

      	val resultsummary = ( (body \ "resultsummary").head ).attributes.asAttrMap
      	//println( resultsummary("api_response1_alt") )

      	val records = (body \ "records").head.child.map(_.attributes.asAttrMap).filter(_.size > 0)
      	//println(records)

      	val kk = for ( r <- (body \ "records").head.child if r.size > 0 ) {
      		r.attributes.asAttrMap ++ ("label", r.label)
      	} 
      	println(kk)


	}

    override def main(args: Array[String]) {
    	scalajCmci()
    }
}