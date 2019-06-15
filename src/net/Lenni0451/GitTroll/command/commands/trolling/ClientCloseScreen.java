package net.Lenni0451.GitTroll.command.commands.trolling;

import net.Lenni0451.GitTroll.command.*;
import net.Lenni0451.GitTroll.utils.*;
import java.util.*;

public class ClientCloseScreen extends CommandBase {
	
	public ClientCloseScreen() {
		super("ClientCloseScreen", "Force a player to close his minecraft client", "<Player>");
	}

	@Override
	public void execute(final CustomPlayer executor, final ArrayHelper args) {
		if (args.isLength(1)) {
			final CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			String closeString = "";
			for (int i = 0; i < 200; ++i) {
				closeString = String.valueOf(closeString) + "\n";
			}
			vic.getPlayer().kickPlayer(closeString);
			executor.sendGitMessage("The player now has to close his minecraft client.");
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(final List<String> tabComplete, final ArrayHelper args) {
		if (args.isEmpty()) {
			this.tabCompletePlayers(tabComplete);
		}
	}
	
}
