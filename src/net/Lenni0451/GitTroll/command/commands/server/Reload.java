package net.Lenni0451.GitTroll.command.commands.server;

import java.util.List;

import org.bukkit.Bukkit;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Reload extends CommandBase {

	public Reload() {
		super("Reload", "Reload the server");
		
		this.addAlias("rl");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			Bukkit.reload();
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

}
