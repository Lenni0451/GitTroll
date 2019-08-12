package net.Lenni0451.GitTroll.command.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class List extends CommandBase {

	public List() {
		super("List", "Get a list of all online players with some information");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			executor.sendGitMessage("Online players:");
			for(Player player : Bukkit.getOnlinePlayers()) {
				executor.sendMessage(" §7- §6" + player.getName() + " §7| §4" + player.getHealth() + "\u2764 §7| §6ID: §4" + player.getEntityId());
			}
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(java.util.List<String> tabComplete, ArrayHelper args) {}

}
