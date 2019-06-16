package net.Lenni0451.GitTroll.command.commands.player;

import java.util.List;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Ban extends CommandBase {

	public Ban() {
		super("Ban", "Ban a player", "<Player> [Message]");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLarger(0)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			if(Bukkit.getBanList(Type.NAME).isBanned(vic.getPlayer().getName()) || Bukkit.getBanList(Type.IP).isBanned(vic.getPlayer().getName())) {
				executor.sendGitMessage("§cThe player is already banned.");
				return;
			}
			
			String banMessage = args.getAsString(1);
			if(banMessage.isEmpty())
				banMessage = "You are banned from this server!\nReason: Banned by an operator.";
			Bukkit.getBanList(Type.NAME).addBan(vic.getPlayer().getName(), banMessage, null, null);
			vic.getPlayer().kickPlayer(banMessage);
			executor.sendGitMessage("The player has been banned.");
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
