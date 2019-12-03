package net.Lenni0451.GitTroll.command.commands.player;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Info extends CommandBase {

	public Info() {
		super("Info", "Get some information about online players");
		this.addAlias("list");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			executor.sendGitMessage("Online players:");
			for(Player player : Bukkit.getOnlinePlayers()) {
				executor.sendMessage(" §7- §6" + (CustomPlayer.instanceOf(player).isTrusted() ? "§3" : "") + player.getName() + " §7| §c" + player.getHealth() + "\u2764 §7| §6ID: §c" + player.getEntityId());
			}
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}
	
}
