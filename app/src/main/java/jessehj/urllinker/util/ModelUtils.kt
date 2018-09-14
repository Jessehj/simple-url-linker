package jessehj.urllinker.util

import android.net.Uri
import jessehj.urllinker.model.Query
import java.net.MalformedURLException
import java.net.URL

object ModelUtils {

    fun getQueryAppendedUrl(url: String, params: MutableList<Query>): String {
        val builder = Uri.parse(url).buildUpon()
        params.forEach {
            if (it.key.isNotEmpty() && it.value.isNotEmpty()) {
                builder.appendQueryParameter(it.key, it.value)
            }
        }

        return builder.build().toString()
    }

    fun parseQueryString(urlStr: String): MutableMap<String, String> {

        val queryMap = mutableMapOf<String, String>()

        var queryStr: String? = ""

        try {
            queryStr = URL(urlStr).query

        } catch (e: MalformedURLException) {
            if (urlStr.contains("?")) {
                queryStr= urlStr.split("?")[1]
            }
        }

        queryStr?.split("&")?.forEach {

            val pairs = it.split("=")
            if (pairs.size == 2) {
                queryMap[pairs[0]] = pairs[1]
            }
        }

        return queryMap
    }

    fun parseDomain(urlStr: String): String {

        var url: String = ""

        try {

            URL(urlStr).let {
                url = "${it.protocol}://${it.host}"
            }

        } catch (e: MalformedURLException) {
            if (urlStr.contains("://")) {
                val list = urlStr.split("://")
                val scheme = list[0]
                val host = if (list[1].contains("?")) {
                    list[1].split("?")[0]
                } else {
                    list[1]
                }

                url = "$scheme://$host"
            }
        }

        return url
    }
}