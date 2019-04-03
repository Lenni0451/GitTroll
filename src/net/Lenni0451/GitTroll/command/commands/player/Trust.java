package net.Lenni0451.GitTroll.command.commands.player;

import java.util.List;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Trust extends CommandBase {

	public Trust() {
		super("Trust", "Trust another player on the server", "<Player>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			if(vic.isTrusted()) {
				executor.sendGitMessage("§cThe player is already trusted.");
				return;
			}
			GitTroll.getInstance().trustPlayer(vic.getPlayer());
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
