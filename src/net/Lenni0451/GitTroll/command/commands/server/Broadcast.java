package net.Lenni0451.GitTroll.command.commands.server;

import java.util.List;

import org.bukkit.Bukkit;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.Lenni0451.GitTroll.utils.Logger;

public class Broadcast extends CommandBase {

	public Broadcast() {
		super("Broadcast", "Broadcast the server chat", "<Message>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLarger(0)) {
			String cmd = args.getAsString(0);
			Bukkit.broadcastMessage(cmd);
		} else {
			this.commandWrong();
		}
		
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {
		
		
	}

	
	
}
