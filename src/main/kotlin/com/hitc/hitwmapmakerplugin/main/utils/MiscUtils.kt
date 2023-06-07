package com.hitc.hitwmapmakerplugin.main.utils

import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.material.Lever
import kotlin.experimental.and

val Player.blockPosition: Location
    get() = this.location.block.location

val Lever.facingDirection: BlockFace
    get() = when (data and 0x7) {
        0x0.toByte() -> BlockFace.EAST
        0x1.toByte() -> BlockFace.EAST
        0x2.toByte() -> BlockFace.WEST
        0x3.toByte() -> BlockFace.SOUTH
        0x4.toByte() -> BlockFace.NORTH
        0x5.toByte() -> BlockFace.NORTH
        0x6.toByte() -> BlockFace.EAST
        0x7.toByte() -> BlockFace.NORTH
        else -> BlockFace.NORTH
    }



object MiscUtils {

}

