package net.Lenni0451.GitTroll.command.commands.player;

import java.util.List;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Heal extends CommandBase {

	public Heal() {
		super("Heal", "Heal you or other players", "[Player]");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(0)) {
			this.execute(executor, new ArrayHelper(args.advanceToStrings(executor.getPlayer().getName())));
		} else if(args.isLength(1)) {
			CustomPlayer vic = CustomPlayer.instanceOf(args.getString(0));
			if(!vic.isValid()) {
				executor.sendGitMessage("§cThe player is not online.");
				return;
			}
			
			vic.getPlayer().setHealth(vic.getPlayer().getMaxHealth());
			vic.getPlayer().setFoodLevel(20);
			vic.getPlayer().setSaturation(20);
			vic.getPlayer().setFireTicks(0);
			if(vic.equals(executor)) {
				executor.sendGitMessage("You have been healed.");
			} else {
				executor.sendGitMessage("The player has been healed.");
			}
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {
		if(args.isLength(0)) {
			this.tabCompletePlayers(tabComplete);
		}
	}

}
