package net.Lenni0451.GitTroll.command.commands.player;

import java.util.List;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Invsee extends CommandBase {

	public Invsee(String name, String description) {
		super("Invsee", "See the Inventory of other palyers");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if (args.isLength(1)) {
            CustomPlayer target = this.parsePlayer(args.getString(0), executor);
            executor.getPlayer().openInventory(target.getPlayer().getInventory());
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
