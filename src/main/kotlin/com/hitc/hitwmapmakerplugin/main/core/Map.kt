package com.hitc.hitwmapmakerplugin.main.core

import com.hitc.hitwmapmakerplugin.main.utils.LocationAsStringSerializer
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.block.BlockFace

@Serializable
data class Map(
    var blocks : HashMap<@Serializable(with = LocationAsStringSerializer::class) Location, WallBlockType>,
    var facings : HashMap<@Serializable(with = LocationAsStringSerializer::class) Location, BlockFace>
)