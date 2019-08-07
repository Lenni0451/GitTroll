package net.Lenni0451.GitTroll.command.commands.plugin;

import java.util.List;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class About extends CommandBase {

	public About() {
		super("About", "Get informations about GitTroll");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			executor.sendGitMessage("GitTroll by §6Lenni0451 §a& §6PaMaBa1806§a.");
			executor.sendGitMessage("Amount of commands: §6" + GitTroll.getInstance().commandManager.getCommands().size());
			executor.sendGitMessage("Thanks for downloading and using §6GitTroll§a!");
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

}
