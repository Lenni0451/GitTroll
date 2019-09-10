package net.Lenni0451.GitTroll.command.commands.player;

import java.util.List;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class EnderchestSee extends CommandBase {

	public EnderchestSee() {
		super("EnderchestSee", "See your or other enderchests", "[Player]");

		this.addAlias("ec");
		this.addAlias("ecopen");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			executor.getPlayer().openInventory(executor.getPlayer().getEnderChest());
		} else if(args.isLength(1)) {
            CustomPlayer target = this.parsePlayer(args.getString(0), executor);
            executor.getPlayer().openInventory(target.getPlayer().getEnderChest());
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
