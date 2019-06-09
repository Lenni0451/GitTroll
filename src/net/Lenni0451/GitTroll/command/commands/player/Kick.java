package net.Lenni0451.GitTroll.command.commands.player;

import java.util.List;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Kick extends CommandBase {

	public Kick() {
		super("Kick", "Kick a player from the server", "<Player> [Message]");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLarger(0)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0),executor);
			
			String msg = args.getAsString(1);
			if(msg.isEmpty()) {
				msg = "Kicked by an operator.";
			}
			msg = this.formatString(msg);
			
			vic.getPlayer().kickPlayer(msg);
			executor.sendGitMessage("The player has been kicked from the server.");
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
