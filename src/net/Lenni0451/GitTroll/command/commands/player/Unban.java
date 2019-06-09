package net.Lenni0451.GitTroll.command.commands.player;

import java.util.List;

import org.bukkit.BanEntry;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Unban extends CommandBase {

	public Unban() {
		super("Unban", "Unban a player", "<Player>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			String name = args.getString(0);

			boolean unbanned = false;
			if(Bukkit.getBanList(Type.NAME).isBanned(name)) {
				Bukkit.getBanList(Type.NAME).pardon(name);
				unbanned = true;
			}
			if(Bukkit.getBanList(Type.IP).isBanned(name)) {
				Bukkit.getBanList(Type.IP).pardon(name);
				unbanned = true;
			}
			if(unbanned)
				executor.sendGitMessage("The player has been unbanned.");
			else
				executor.sendGitMessage("§cThe player is not banned.");
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {
		if(args.isEmpty()) {
			for(BanEntry banEntry : Bukkit.getBanList(Type.NAME).getBanEntries()) {
				tabComplete.add(banEntry.getTarget());
			}
			for(BanEntry banEntry : Bukkit.getBanList(Type.IP).getBanEntries()) {
				tabComplete.add(banEntry.getTarget());
			}
		}
	}

}
