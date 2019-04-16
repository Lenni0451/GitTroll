package net.Lenni0451.GitTroll.command.commands.plugin;

import java.util.List;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Help extends CommandBase {

	public Help() {
		super("Help", "See a list of commands with their description", "[Help Page]");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(0)) {
			this.execute(executor, new ArrayHelper(args.advanceToStrings("1")));
		} else if(args.isLength(1) && args.isInteger(0)) {
			int page = args.getInteger(0);
			final int commandsPerPage = 5;
			final int pageCount = Double.valueOf(Math.ceil((float) GitTroll.getInstance().commandManager.getCommands().size() / (float) commandsPerPage)).intValue();
			
			if(page > pageCount || page <= 0) {
				executor.sendGitMessage("§cThe help page could not be found!");
				return;
			}
			
			executor.sendGitMessage("§7----------------- §6Help " + page + "§7/§6" + pageCount + " §7-----------------");
			for(int i = 0; i < commandsPerPage; i++) {
				int index = i + ((page - 1) * commandsPerPage);
				if(index > GitTroll.getInstance().commandManager.getCommands().size() - 1) {
					break;
				}
				CommandBase cmd = GitTroll.getInstance().commandManager.getCommands().get(index);
				executor.sendGitMessage(" §7- §6" + cmd.getName() + " §7| §a" + cmd.getDescription());
			}
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

}
