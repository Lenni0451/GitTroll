package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class ChunkRenderGlitch extends CommandBase {

	public ChunkRenderGlitch() {
		super("ChunkRenderGlitch", "Let unloaded chunks glitch for a player", "<Player>");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			int range = 10;
			Material block = Material.DIAMOND_BLOCK;
			
			while(true) {
				try {
					Material[] materials = Material.values();
					block = materials[ThreadLocalRandom.current().nextInt(materials.length)];
					if(block.isBlock()) {
						break;
					}
				} catch (Exception e) {}
			}

			final Material finalBlock = block;
			Bukkit.getScheduler().runTask(GitTroll.getInstance(), () -> {
				for(int x = -range; x <= range; x++) {
					for(int y = -range; y <= range; y++) {
						for(int z = -range; z <= range; z++) {
							Location newLoc = vic.getPlayer().getLocation().add(x * 80, y, z * 50);
							Material origType = newLoc.getBlock().getType();
							if(!origType.equals(Material.AIR) && !origType.equals(finalBlock)) {
								vic.getPlayer().sendBlockChange(newLoc, finalBlock, (byte)0);
							}
						}
					}
				}
				
				executor.sendGitMessage("The unloaded chunks of the player should now be glitched.");
			});
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {
		if(args.isEmpty()) {
			this.tabCompletePlayers(tabComplete);
		}
	}

}
