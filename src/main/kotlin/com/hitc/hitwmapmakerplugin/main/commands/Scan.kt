package com.hitc.hitwmapmakerplugin.main.commands

import com.hitc.hitwmapmakerplugin.main.MainObject
import com.hitc.hitwmapmakerplugin.main.core.Map
import com.hitc.hitwmapmakerplugin.main.core.WallBlockType
import com.hitc.hitwmapmakerplugin.main.utils.MapFileUtils
import com.hitc.hitwmapmakerplugin.main.utils.MiscUtils
import com.hitc.hitwmapmakerplugin.main.utils.blockPosition
import com.hitc.hitwmapmakerplugin.main.utils.facingDirection
import com.sk89q.worldedit.BlockVector
import org.bukkit.DyeColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.TreeSpecies
import org.bukkit.block.BlockFace
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.material.*
import kotlin.experimental.and

class Scan : CommandExecutor {

    private var wall : Map = Map(HashMap(), HashMap())


    override fun onCommand(
        sender: CommandSender?,
        command: Command?,
        label: String?,
        args: Array<out String>?
    ): Boolean {
        val p = sender as? Player ?: return true
        val name = if (args?.isNotEmpty() == true) args[0] else
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
            Material.WOOL -> {
                var color : DyeColor? = null
                (blockData as? Colorable)?.color?.let{ color = it }

                when (color) {
                    DyeColor.GREEN  -> wall.blocks[location] = WallBlockType.WALL
                    DyeColor.YELLOW -> wall.blocks[location] = WallBlockType.WALL2
                    DyeColor.RED    -> wall.blocks[location] = WallBlockType.WALL_UNUSED
                    DyeColor.BLACK  -> wall.blocks[location] = WallBlockType.SUPPORT_START
                    else -> return
                }
            }
            Material.WOOD -> {
                // oak
                if ((blockData as? Tree)?.species == TreeSpecies.GENERIC) {
                    wall.blocks[location] = WallBlockType.WOOD1
                }
                // spruce
                else if ((blockData as? Tree)?.species == TreeSpecies.REDWOOD) {
                    wall.blocks[location] = WallBlockType.WOOD2
                }
            }
            Material.SPRUCE_WOOD_STAIRS -> {
                wall.blocks[location] = WallBlockType.WOOD_STAIR
                wall.facings[location] = (blockData as? Stairs)?.facing ?: return
            }
            Material.LEVER -> {
                wall.blocks[location] = WallBlockType.LEVER
                wall.facings[location] = (blockData as Lever).facingDirection
            }
            Material.COBBLE_WALL -> {
                wall.blocks[location] = WallBlockType.SUPPORT
            }
            Material.CARPET -> {
                wall.blocks[location] = WallBlockType.CARPET
            }
            Material.STAINED_GLASS -> {
                wall.blocks[location] = WallBlockType.GLASS
            }
            Material.STAINED_CLAY -> {
                if (block.data == DyeColor.BLACK.data) {
                    wall.blocks[location] = WallBlockType.SUPPORT_END
                }
            }

            else -> return
        }
    }
}