package com.codepatissier.keki.src.main.login

import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.Charset

fun GetRedirectUrl(url: String?): String? {
   var urlTmp: URL? = null
   var redUrl: String? = null
   var connection: HttpURLConnection? = null
   try {
      urlTmp = URL(url)
   } catch (e1: MalformedURLException) {
      e1.printStackTrace()
   }
   try {
      connection = urlTmp!!.openConnection() as HttpURLConnection
   } catch (e: IOException) {
      e.printStackTrace()
   }
   try {
      connection!!.responseCode
   } catch (e: IOException) {
      e.printStackTrace()
   }
   redUrl = connection!!.url.toString()
   connection.disconnect()
   return redUrl
}

@Throws(IOException::class)
private fun jsonReadAll(reader: Reader): String {
   val sb = StringBuilder()
   var cp: Int
   while (reader.read().also { cp = it } != -1) {
      sb.append(cp.toChar())
   }
   return sb.toString()
}


@Throws(IOException::class, JSONException::class)
fun readJsonFromUrl(url: String): JSONObject? {
   val iss = URL(url).openStream()
   return iss.use { iss ->
      val rd = BufferedReader(InputStreamReader(iss, Charset.forName("UTF-8")))
      val jsonText = jsonReadAll(rd)
      JSONObject(jsonText)
   }
}