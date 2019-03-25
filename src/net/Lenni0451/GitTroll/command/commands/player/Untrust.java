package net.Lenni0451.GitTroll.command.commands.player;

import java.util.List;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Untrust extends CommandBase {

	public Untrust() {
		super("Untrust", "Remove yourself from the trust list");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.getLength() == 0) {
			GitTroll.getInstance().untrustPlayer(executor.getPlayer());
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

}
