package com.hitc.hitwmapmakerplugin.main

import com.hitc.hitwmapmakerplugin.main.commands.Scan
import com.sk89q.worldedit.bukkit.WorldEditPlugin
import com.sk89q.worldedit.bukkit.adapter.BukkitImplLoader
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin(), Listener {

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
        registerCommands()


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
    val worldEditPlugin : WorldEditPlugin = worldEdit as? WorldEditPlugin ?: throw Exception("worldedit not installed")
}

