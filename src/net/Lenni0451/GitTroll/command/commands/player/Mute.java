package net.Lenni0451.GitTroll.command.commands.player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Mute extends CommandBase implements Listener {

	List<CustomPlayer> mutedUsers = new ArrayList<>();
	
	public Mute() {
		super("Mute", "Silence a player from the chat", "<Player>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			if(this.mutedUsers.remove(vic)) {
				executor.sendGitMessage("§cThe player is no longer muted.");
			} else {
				this.mutedUsers.add(vic);
				executor.sendGitMessage("The player is now unable to write into chat.");
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
	public void onChat(AsyncPlayerChatEvent event) {
		if(this.mutedUsers.contains(CustomPlayer.instanceOf(event.getPlayer()))) {
			event.setCancelled(true);
		}
	}

}
