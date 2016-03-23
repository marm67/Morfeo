// Connect through a Proxy : http://www.rgagnon.com/javadetails/java-0085.html

import org.apache.commons.codec.binary.Base64
import java.net.URL
import java.net.Proxy
import scala.io.Source

object HTTPRequest {
	object HttpBasicAuth {
	   val BASIC = "Basic"
	   val AUTHORIZATION = "Authorization"

	   def encodeCredentials(username: String, password: String): String = {
	      new String(Base64.encodeBase64String((username + ":" + password).getBytes))
	   }

	   def getHeader(username: String, password: String): String = 
	      BASIC + " " + encodeCredentials(username, password)
	}

	def apply(url: String) = {
      val connection = new URL(url).openConnection(Proxy.NO_PROXY)
      Source.fromInputStream(connection.getInputStream)
	}

	def apply(url: String, username: String, password: String) = {
      val connection = new URL(url).openConnection(Proxy.NO_PROXY)
      connection.setRequestProperty( HttpBasicAuth.AUTHORIZATION, HttpBasicAuth.getHeader(username, password) )
      Source.fromInputStream(connection.getInputStream)
	}
}