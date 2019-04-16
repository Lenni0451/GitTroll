package net.Lenni0451.GitTroll.utils;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.Lenni0451.GitTroll.GitTroll;

public class BungeeUtils {
	
	private final Player player;
	
	public BungeeUtils(final Player player) {
		this.player = player;
	}
	
	public void sendBungeeMessageToPlayer(final String... messages) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		for(String msg : messages) {
	        out.writeUTF(msg);
		}
        this.player.sendPluginMessage(GitTroll.getInstance(), "BungeeCord", out.toByteArray());
	}
	
}
