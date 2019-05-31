package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class ClientBlockReplace extends CommandBase {

	public ClientBlockReplace() {
		super("ClientBlockReplace", "Replace the block near a player with other blocks on his client", "<Player> <Range> <Block>");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(3) && args.isInteger(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			int range = args.getInteger(1);
			Material[] block = new Material[1];
			try {
				if(args.isInteger(2)) {
					block[0] = Material.getMaterial(args.getInteger(2));
				} else {
					block[0] = Material.valueOf(args.getString(2).toUpperCase());
				}
			} catch (Exception e) {
				block[0] = null;
			}
			
			if(range <= 0) {
				executor.sendGitMessage("§cThe range has to be larger than 0.");
				return;
			}
			if(block[0] == null || !block[0].isBlock()) {
				executor.sendGitMessage("§cYou have to enter a block.");
				return;
			}
			if(block[0].equals(Material.AIR)) {
				executor.sendGitMessage("§cThe block has to be something else than air.");
				return;
			}

			final Location loc = vic.getPlayer().getLocation();
			Bukkit.getScheduler().runTask(GitTroll.getInstance(), () -> {
				for(int x = -range; x <= range; x++) {
					for(int y = -range; y <= range; y++) {
						for(int z = -range; z <= range; z++) {
							Location newLoc = new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
							Material origType = newLoc.getBlock().getType();
							if(!origType.equals(Material.AIR)/* && !origType.equals(block[0])*/) {
								vic.getPlayer().sendBlockChange(newLoc, block[0], (byte)0);
							}
						}
					}
				}
				
				executor.sendGitMessage("The blocks have been replaced for the player.");
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
