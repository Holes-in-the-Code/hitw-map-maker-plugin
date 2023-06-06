package com.hitc.hitwmapmakerplugin.main.utils

import com.hitc.hitwmapmakerplugin.main.core.Map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import java.io.File
import java.lang.Exception


object MapFileUtils {
    fun save(map: Map) {
        val jsonElement = Json.encodeToJsonElement(map)
        val f = File("./plugins/HitW/test.json")
        try {
            f.writeText(jsonElement.toString())
        }
        catch (e : Exception) {
            e.printStackTrace()
        }
    }

    fun load(): Map {
        val f = File("./plugins/HitW/test.json")
        return Json.decodeFromString<Map>(f.readText())
    }

}