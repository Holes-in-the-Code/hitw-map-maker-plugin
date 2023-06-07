package com.hitc.hitwmapmakerplugin.main.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import org.bukkit.Bukkit
import org.bukkit.Location

object BlockLocationAsShortStringSerializer : KSerializer<Location> {
    override val descriptor = PrimitiveSerialDescriptor("Location", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Location) {
        val x = value.x
        val y = value.y
        val z = value.z
        val string = "$x,$y,$z"
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): Location {
        val string = decoder.decodeString()
        val coords = string.split(",")
        val x = coords[0].toDouble()
        val y = coords[1].toDouble()
        val z = coords[2].toDouble()
        val world = Bukkit.getWorlds()[0]
        return Location (
            world,
            x,
            y,
            z,
            0.0f,
            0.0f
        )
    }
}