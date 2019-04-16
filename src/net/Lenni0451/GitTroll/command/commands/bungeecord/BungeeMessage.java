package net.Lenni0451.GitTroll.command.commands.bungeecord;

import java.util.List;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.BungeeUtils;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class BungeeMessage extends CommandBase {

	public BungeeMessage() {
		super("BungeeMessage", "Send a message to a player on the bungee", "<Player> <Message>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLarger(1)) {
			String player = args.getString(0);
			String message = args.getAsString(1).replace("&", "§").replace("§§", "&");
			
			BungeeUtils bu = new BungeeUtils(executor.getPlayer());
			bu.sendBungeeMessageToPlayer("Message", player, message);
			
			executor.sendGitMessage("The message has been sent to the bungee.");
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
