package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.util.Vector;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class PissRocket extends CommandBase {

	private List<CustomPlayer> players = new ArrayList<>();
	
	@SuppressWarnings("deprecation")
	public PissRocket() {
		super("PissRocket", "Let a player piss himself", "<Player>");
		
		Bukkit.getScheduler().runTaskTimer(GitTroll.getInstance(), () -> {
			try {
				for(CustomPlayer player : this.players) {
					player.getPlayer().setVelocity(new Vector(0, 1, 0));
					player.getPlayer().getWorld().playSound(player.getPlayer().getLocation(), Sound.FIZZ, 10, 1);
					player.getPlayer().getWorld().spawnFallingBlock(player.getLocation(), Material.WOOL, (byte)4);
				}
			} catch (Throwable e) {}
		}, 1, 1);
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			if(players.remove(vic)) {
				vic.getPlayer().setAllowFlight(vic.getPlayer().getGameMode().equals(GameMode.CREATIVE) || vic.getPlayer().getGameMode().equals(GameMode.SPECTATOR));
				executor.sendGitMessage("§cThe player no longer pisses him self.");
			} else {
				vic.getPlayer().setAllowFlight(true);
				this.players.add(vic);
				executor.sendGitMessage("The player now pisses him self.");
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

}
