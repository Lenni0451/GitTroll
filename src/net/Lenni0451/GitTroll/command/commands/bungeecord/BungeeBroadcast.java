package net.Lenni0451.GitTroll.command.commands.bungeecord;

import java.util.List;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.BungeeUtils;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class BungeeBroadcast extends CommandBase {

	public BungeeBroadcast() {
		super("BungeeBroadcast", "Send a message to all players on the bungee", "<Message>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLarger(0)) {
			String message = args.getAsString().replace("&", "§").replace("§§", "&");
			
			BungeeUtils bu = new BungeeUtils(executor.getPlayer());
			bu.sendBungeeMessageToPlayer("Message", "ALL", message);
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

}
