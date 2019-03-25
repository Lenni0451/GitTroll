package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.List;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Explode extends CommandBase {

	public Explode() {
		super("Explode", "Explode a player", "<Player> [Explosion Strength]");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			this.execute(executor, new ArrayHelper(args.advanceToStrings("10")));
		} else if(args.isLength(2) && args.isFloat(1)) {
			CustomPlayer vic = CustomPlayer.instanceOf(args.getString(0));
			if(!vic.isValid()) {
				executor.sendGitMessage("§cThe player is not online.");
				return;
			}
			
			vic.getPlayer().getWorld().createExplosion(vic.getPlayer().getLocation(), args.getFloat(1));
			executor.sendGitMessage("The player has been exploded.");
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
