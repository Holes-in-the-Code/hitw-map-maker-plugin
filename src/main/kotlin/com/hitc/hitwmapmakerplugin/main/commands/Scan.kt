package com.hitc.hitwmapmakerplugin.main.commands

import com.hitc.hitwmapmakerplugin.main.MainObject
import com.hitc.hitwmapmakerplugin.main.core.Map
import com.hitc.hitwmapmakerplugin.main.core.WallBlockType
import com.hitc.hitwmapmakerplugin.main.utils.MapFileUtils
import com.hitc.hitwmapmakerplugin.main.utils.blockPosition
import com.sk89q.worldedit.BlockVector
import com.sk89q.worldedit.blocks.ClothColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.material.Lever
import org.bukkit.material.Stairs

class Scan : CommandExecutor {

    private var wall : Map = Map(HashMap(), HashMap())


    override fun onCommand(
        sender: CommandSender?,
        command: Command?,
        label: String?,
        args: Array<out String>?
    ): Boolean {
        val p = sender as? Player ?: return true
        val name = args?.get(0) ?:
            run{p.sendMessage("§cInvalid name given."); return true}
        val selection = MainObject.worldEditPlugin.getSelection(p) ?:
            run{p.sendMessage("§cNothing selected."); return true}
        val region = selection.regionSelector.region

        p.sendMessage("§aScanning...")
        region.forEach{ scan(it, p, wall) }
        p.sendMessage("§aScan completed")

        MapFileUtils.save(wall, name)

        return true
    }

    private fun scan(blockPos : BlockVector, p : Player, wall : Map) {
        val location = Location(p.world, blockPos.x, blockPos.y, blockPos.z)
        val block = p.world.getBlockAt(location)
        val blockData = block.state.data

        location.subtract(p.blockPosition)

        when (block.type) {
            Material.WOOL,Material.STAINED_GLASS,Material.STAINED_CLAY -> {
                when (ClothColor.fromID(block.data.toInt())) {
                    ClothColor.DARK_GREEN -> {
                        if (block.type == Material.WOOL) wall.blocks[location] = WallBlockType.WALL
                        if (block.type == Material.STAINED_GLASS) wall.blocks[location] = WallBlockType.GLASS
                    }
                    ClothColor.YELLOW -> wall.blocks[location] = WallBlockType.WALL2
                    ClothColor.RED -> wall.blocks[location] = WallBlockType.WALL_UNUSED
                    ClothColor.BLACK -> {
                        if (block.type == Material.WOOL) wall.blocks[location] = WallBlockType.SUPPORT_START
                        if (block.type == Material.STAINED_CLAY) wall.blocks[location] = WallBlockType.SUPPORT_END

                    }
                    else -> return
                }
            }
            Material.WOOD -> {
                // oak
                if (block.data.toInt() == 0) {
                    wall.blocks[location] = WallBlockType.WOOD1
                }
                // spruce
                else if (block.data.toInt() == 1) {
                    wall.blocks[location] = WallBlockType.WOOD2
                }
            }
            Material.SPRUCE_WOOD_STAIRS -> {
                wall.blocks[location] = WallBlockType.WOOD_STAIR
                wall.facings[location] = (blockData as? Stairs)?.facing ?: return
            }
            Material.LEVER -> {
                wall.blocks[location] = WallBlockType.LEVER
                wall.facings[location] = (blockData as? Lever)?.facing ?: return
            }
            Material.COBBLE_WALL -> {
                wall.blocks[location] = WallBlockType.SUPPORT
            }

            else -> return
        }
    }
}