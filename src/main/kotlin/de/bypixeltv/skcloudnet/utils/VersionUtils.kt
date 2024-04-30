package de.bypixeltv.skcloudnet.utils

import ch.njol.skript.Skript
import org.json.JSONObject

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.math.max

class VersionUtils() {

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

    @Suppress("DEPRECATION")
    fun getSkriptVersion(): String {
        return Skript.getInstance().description.version
    }

    fun getLatestAddonVersion(): String? {
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

    fun isVersionGreater(version1: String, version2: String): Boolean {
        val parts1 = version1.split(".")
        val parts2 = version2.split(".")

        val length = max(parts1.size, parts2.size)
        for (i in 0 until length) {
            val part1 = parts1.getOrNull(i)?.toInt() ?: 0
            val part2 = parts2.getOrNull(i)?.toInt() ?: 0

            if (part1 > part2) {
                return true
            } else if (part1 < part2) {
                return false
            }
        }

        return false
    }

}