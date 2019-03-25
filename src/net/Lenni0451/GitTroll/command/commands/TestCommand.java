package net.Lenni0451.GitTroll.command.commands;

import java.util.List;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class TestCommand extends CommandBase {

	public TestCommand() {
		super("Test", "A test command to see if everything works");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		executor.sendGitMessage("It works!");
		executor.sendGitMessage("You passed " + args.getLength() + " arguments.");
		executor.sendGitMessage(args.getAsString());
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {
		if(args.getLength() >= 1)
			tabComplete.add(args.getString(0));
		else
			tabComplete.add("Git");
	}

}
