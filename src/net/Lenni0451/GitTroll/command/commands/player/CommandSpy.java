package net.Lenni0451.GitTroll.command.commands.player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class CommandSpy extends CommandBase implements Listener {
	
	List<CustomPlayer> player = new ArrayList<>();

	public CommandSpy() {
		super("CommandSpy", "See the commands of players");
		this.addAlias("cmdspy");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			if(this.player.contains(executor)) {
				executor.sendGitMessage("§cCommandSpy has been disabled.");
				this.player.remove(executor);
			} else {
				executor.sendGitMessage("CommandSpy has been enabled.");
				this.player.add(executor);
			}
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}
	
	@EventHandler
	public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
		for(CustomPlayer player : this.player) {
			if(!player.getPlayer().equals(event.getPlayer()))
				player.sendMessage("§7[§6CmdSpy§7] §e" + event.getPlayer().getName() + " §a" + event.getMessage());
		}
	}

}
