package com.hitc.hitwmapmakerplugin.main.utils

import com.hitc.hitwmapmakerplugin.main.core.Map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import java.io.File
import java.lang.Exception


object MapFileUtils {
    fun save(map: Map, name : String) {
        val jsonElement = Json.encodeToJsonElement(map)
        val f = File("./plugins/HitW/$name.json")
        try {
            f.writeText(jsonElement.toString())
        }
        catch (e : Exception) {
            e.printStackTrace()
        }
    }

    fun load(name : String): Map {
        val f = File("./plugins/HitW/$name.json")
        return Json.decodeFromString<Map>(f.readText())
    }

}