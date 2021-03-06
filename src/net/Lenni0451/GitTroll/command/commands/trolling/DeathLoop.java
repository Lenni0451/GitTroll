package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class DeathLoop extends CommandBase {

	private List<CustomPlayer> players = new ArrayList<>();
	
	public DeathLoop() {
		super("DeathLoop", "Kill a player in an infinite loop", "<Player>");
		
		Bukkit.getScheduler().runTaskTimer(GitTroll.getInstance().getParentPlugin(), () -> {
			try {
				for(CustomPlayer player : players) {
					player.kill();
				}
			} catch (Exception e) {}
		}, 1, 1);
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			if(this.players.remove(vic)) {
				executor.sendGitMessage("�cThe player is now free to go.");
			} else {
				this.players.add(vic);
				executor.sendGitMessage("The player is now stuck in a death loop.");
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
