package net.Lenni0451.GitTroll.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public abstract class CommandBase {
	
	private final String name;
	private final String description;
	private final String help;
	private final List<String> aliases;
	
	public CommandBase(final String name, final String description) {
		this(name, description, "");
	}
	
	public CommandBase(final String name, final String description, final String help) {
		this.name = name;
		this.description = description;
		this.help = help;
		this.aliases = new ArrayList<>();
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}
	
	public String getHelp() {
		return this.help;
	}
	
	public List<String> getAliases() {
		return this.aliases;
	}
	
	
	protected void commandWrong() {
		throw new CommandWrongException();
	}
	
	protected void addAlias(final String alias) {
		if(!this.aliases.contains(alias.toLowerCase()))
			this.aliases.add(alias.toLowerCase());
	}
	
	protected void tabCompletePlayers(final List<String> tabComplete) {
		for(Player player : Bukkit.getOnlinePlayers()) {
			tabComplete.add(player.getName());
		}
	}
	
	protected CustomPlayer parsePlayer(final String name, final CustomPlayer executor) {
		CustomPlayer player = CustomPlayer.instanceOf(name);
		if(player == null || !player.isValid()) {
			executor.sendGitMessage("§cThe player is not online.");
			throw new ReturnException();
		}
		return player;
	}
	
	public abstract void execute(final CustomPlayer executor, final ArrayHelper args);
	public abstract void tabComplete(final List<String> tabComplete, final ArrayHelper args);
	
}
