package com.hitc.hitwmapmakerplugin.main.core

import org.bukkit.entity.Player

class Editor(private var player : Player) {

    fun getPlayer() : Player {
        return this.player
    }

}