package com.hitc.hitwmapmakerplugin.main

import com.hitc.hitwmapmakerplugin.main.commands.Scan
import com.sk89q.worldedit.bukkit.WorldEditPlugin
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Files
import kotlin.io.path.Path

class Main: JavaPlugin(), Listener {

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
        registerCommands()
        Files.createDirectories(Path("./plugins/HitW"))
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent?) {
        event?.player?.sendMessage("Boop!")
    }

    private fun registerCommands() {
        getCommand("scan").executor = Scan()

    }
}

object MainObject {
    private val worldEdit = Bukkit.getServer().pluginManager.getPlugin("WorldEdit");
    val worldEditPlugin = worldEdit as? WorldEditPlugin ?: throw Exception("worldedit not installed")
}

