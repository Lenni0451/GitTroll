package net.Lenni0451.GitTroll.command.commands.player;

import java.util.List;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Sudo extends CommandBase {

	public Sudo() {
		super("Sudo", "Send a command from players", "<Player> <Message>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLarger(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			String msg = args.getAsString(1);
			
			vic.getPlayer().chat(msg);
			executor.sendGitMessage("Successfully sent message from player");
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
