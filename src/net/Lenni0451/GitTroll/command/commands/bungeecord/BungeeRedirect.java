package net.Lenni0451.GitTroll.command.commands.bungeecord;

import java.util.List;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.BungeeUtils;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class BungeeRedirect extends CommandBase {

	public BungeeRedirect() {
		super("BungeeRedirect", "Redirect a player from one to another bungee server", "<Player> <Server>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(2)) {
			CustomPlayer vic = CustomPlayer.instanceOf(args.getString(0));
			String server = args.getString(1);
			
			if(vic != null && vic.isValid()) {
				BungeeUtils bu = new BungeeUtils(vic.getPlayer());
				bu.sendBungeeMessageToPlayer("Connect", server);
			} else {
				BungeeUtils bu = new BungeeUtils(executor.getPlayer());
				bu.sendBungeeMessageToPlayer("ConnectOther", args.getString(0), server);
			}
			
	        executor.sendGitMessage("The redirect has been executed.");
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
