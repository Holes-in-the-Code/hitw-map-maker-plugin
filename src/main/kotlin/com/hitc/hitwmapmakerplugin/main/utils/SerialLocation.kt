package com.hitc.hitwmapmakerplugin.main.utils

import kotlinx.serialization.Serializable

@Serializable
data class SerialLocation(
    val world : String,
    val x : Double,
    val y : Double,
    val z : Double,
    val yaw : Float,
    val pitch : Float
)

