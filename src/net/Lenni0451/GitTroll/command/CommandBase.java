package net.Lenni0451.GitTroll.command;

import java.util.List;

import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public abstract class CommandBase {
	
	private final String name;
	private final String description;
	private final String help;
	
	public CommandBase(final String name, final String description) {
		this(name, description, "");
	}
	
	public CommandBase(final String name, final String description, final String help) {
		this.name = name;
		this.description = description;
		this.help = help;
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
	
	
	protected void commandWrong() {
		throw new CommandWrongException();
	}
	
	public abstract void execute(final CustomPlayer executor, final ArrayHelper args);
	public abstract void tabComplete(final List<String> tabComplete, final ArrayHelper args);
	
}
