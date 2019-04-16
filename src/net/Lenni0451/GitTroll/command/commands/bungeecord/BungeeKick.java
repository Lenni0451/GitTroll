package net.Lenni0451.GitTroll.command.commands.bungeecord;

import java.util.List;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.BungeeUtils;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class BungeeKick extends CommandBase {

	public BungeeKick() {
		super("BungeeKick", "Kick a player from the bungee server", "<Player> [Kick Message]");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLarger(0)) {
			BungeeUtils bu = new BungeeUtils(executor.getPlayer());
			bu.sendBungeeMessageToPlayer("KickPlayer", args.getString(0), (args.isLarger(1)?args.getAsString(1).replace("&", "§").replace("§§", "&"):"Connection timed out"));
			
			executor.sendGitMessage("The kick command has been sent to the bungee.");
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
