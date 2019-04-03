package net.Lenni0451.GitTroll.command.commands.server;

import java.util.List;

import org.bukkit.Bukkit;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Stop extends CommandBase {

	public Stop() {
		super("Stop", "Stop the server");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			Bukkit.shutdown();
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

}
