package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class FartRocket extends CommandBase implements Listener {

	private List<CustomPlayer> players = new ArrayList<>();
	
	public FartRocket() {
		super("FartRocket", "Let a player fart", "<Player>");
		
		Bukkit.getScheduler().runTaskTimer(GitTroll.getInstance(), () -> {
			try {
				for(CustomPlayer player : this.players) {
					player.getPlayer().setVelocity(new Vector(0, 1, 0));
					player.getPlayer().getWorld().playSound(player.getPlayer().getLocation(), Sound.BURP, 10, 1);
					player.getPlayer().getWorld().playEffect(player.getPlayer().getLocation().add(0, 1, 0), Effect.COLOURED_DUST, 10);
					player.getPlayer().setSneaking(!player.getPlayer().isSneaking());
				}
			} catch (Throwable e) {}
		}, 0, 1);
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			if(players.remove(vic)) {
				vic.getPlayer().setAllowFlight(false);
				executor.sendGitMessage("§cThe player is no longer farting.");
			} else {
				vic.getPlayer().setAllowFlight(true);
				this.players.add(vic);
				executor.sendGitMessage("The player is now farting.");
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
