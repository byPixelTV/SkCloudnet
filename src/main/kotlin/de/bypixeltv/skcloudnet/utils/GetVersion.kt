package de.bypixeltv.skcloudnet.utils

import org.json.JSONObject

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class GetVersion() {

    fun getLatestSkriptVersion(): String? {
        val httpClient = HttpClient.newHttpClient()
        val request =
            HttpRequest.newBuilder().uri(URI.create("https://api.github.com/repos/SkriptLang/Skript/releases/latest"))
                .build()
        try {
            val body = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).get().body()
            return JSONObject(body).getString("tag_name")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getLatestGithubAddonVersion(): String? {
        val httpClient = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.github.com/repos/byPixelTV/SkCloudnet/releases/latest")).build()
        try {
            val body = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).get().body()
            return JSONObject(body).getString("tag_name")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}