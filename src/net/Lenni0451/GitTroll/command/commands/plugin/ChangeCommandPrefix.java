package net.Lenni0451.GitTroll.command.commands.plugin;

import java.util.List;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.manager.CommandManager;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.Lenni0451.GitTroll.utils.Logger;

public class ChangeCommandPrefix extends CommandBase {

	public ChangeCommandPrefix() {
		super("ChangeCommandPrefix", "Change the command prefix of ultra troll commands", "<Prefix>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			String prefix = args.getString(0);
			
			if(prefix.length() > 1) {
				executor.sendGitMessage("§cThe command prefix can only be 1 char.");
				return;
			}
			if(prefix.equalsIgnoreCase("/")) {
				executor.sendGitMessage("§cThe command prefix can not be a /.");
				return;
			}
			
			CommandManager.COMMAND_PREFIX = prefix;
			
			Logger.broadcastGitMessage("The player §6" + executor.getPlayer().getName() + " §achanged the command prefix to §6" + prefix + "§a.");
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

}
