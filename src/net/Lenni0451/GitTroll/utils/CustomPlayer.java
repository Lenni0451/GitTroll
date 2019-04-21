package net.Lenni0451.GitTroll.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.Lenni0451.GitTroll.GitTroll;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;

public class CustomPlayer {
	
	private static final Map<Player, CustomPlayer> initializedPlayers = new HashMap<>();
	
	public static CustomPlayer instanceOf(final String name) {
		Player player = Bukkit.getPlayer(name);
		if((player == null || !player.isOnline()) && NumberUtils.isInteger(name)) {
			for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
				if(onlinePlayer.getEntityId() == Integer.valueOf(name)) {
					player = onlinePlayer;
					break;
				}
			}
		}
		return instanceOf(player);
	}
	
	public static CustomPlayer instanceOf(final Player player) {
		if(player == null) return null;
		if(initializedPlayers.containsKey(player)) return initializedPlayers.get(player);
		
		CustomPlayer customPlayer = new CustomPlayer(player);
		initializedPlayers.put(player, customPlayer);
		return customPlayer;
	}
	
	
	private final Player player;
	
	private CustomPlayer(final Player player) {
		this.player = player;
	}
	

	public Player getPlayer() {
		return this.player;
	}

	public CraftPlayer getCraftPlayer() {
		return (CraftPlayer) this.player;
	}
	
	
	public boolean isValid() {
		return this.player != null && this.player.isOnline();
	}

	public boolean isTrusted() {
		return GitTroll.getInstance().isPlayerTrusted(this.player);
	}
	
	
	public void sendPacket(final Packet<?> packet) {
		this.getCraftPlayer().getHandle().playerConnection.sendPacket(packet);
	}

	public void sendMessage(final String message) {
		this.player.sendMessage(message);
	}
	
	public void sendGitMessage(final String message) {
		this.player.sendMessage(GitTroll.PREFIX + message);
	}
	
	public void sudo(final String message) {
		this.player.chat(message);
	}

	public void sendTitle(final String title, final String subtitle) {
		this.sendPacket(new PacketPlayOutTitle(EnumTitleAction.TITLE, new ChatComponentText(title)));
		this.sendPacket(new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, new ChatComponentText(subtitle)));
		this.sendPacket(new PacketPlayOutTitle(10, 20 * 3, 10));
	}
	
}
