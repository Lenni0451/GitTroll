package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class ItemScareOff extends CommandBase implements Listener {

	private List<CustomPlayer> players = new ArrayList<>();

	public ItemScareOff() {
		super("ItemScareOff", "", "<Player>");
		
		Bukkit.getScheduler().runTaskTimer(GitTroll.getInstance(), () -> {
			try {
				double divider = 9;
				int range = 3;
				for(CustomPlayer player : players) {
					try {
						List<Entity> entitys = player.getPlayer().getNearbyEntities(range, range, range);
						for(Entity e : entitys) {
							if(e.getType() == EntityType.DROPPED_ITEM) {
								e.setVelocity(new Vector((e.getLocation().getX() - player.getLocation().getX()) / divider, (e.getLocation().getY() - player.getLocation().getY()) / divider, (e.getLocation().getZ() - player.getLocation().getZ()) / divider));
							}
						}
					} catch (Exception e) {}
				}
			} catch (Throwable e) {}
		}, 0, 1);
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			if(players.remove(vic)) {
				executor.sendGitMessage("§cItems are no longer scared of that player.");
			} else {
				this.players.add(vic);
				executor.sendGitMessage("Items are now scared of that player.");
			}
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
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		this.players.remove(CustomPlayer.instanceOf(event.getPlayer()));
	}
	
	@EventHandler
	public void onPickup(PlayerPickupItemEvent event) {
		if(this.players.contains(CustomPlayer.instanceOf(event.getPlayer()))) {
			event.setCancelled(true);
		}
	}

}
