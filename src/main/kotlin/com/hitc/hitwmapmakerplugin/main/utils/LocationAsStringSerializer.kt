package com.hitc.hitwmapmakerplugin.main.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import org.bukkit.Bukkit
import org.bukkit.Location

object LocationAsStringSerializer : KSerializer<Location> {
    override val descriptor = PrimitiveSerialDescriptor("Location", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Location) {
        val serialLocation = SerialLocation (
            value.world.name,
            value.x,
            value.y,
            value.z,
            value.pitch,
            value.yaw
        )
        val string = Json.encodeToString(serialLocation)
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): Location {
        val string = decoder.decodeString()
        val serialLocation = Json.decodeFromString<SerialLocation>(string)
        val world = Bukkit.getWorld(serialLocation.world) ?: throw IllegalArgumentException("unknown world")
        return Location (
            world,
            serialLocation.x,
            serialLocation.y,
            serialLocation.z,
            serialLocation.pitch,
            serialLocation.yaw
        )
    }
}


