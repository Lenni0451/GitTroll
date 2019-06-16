package net.Lenni0451.GitTroll.command.commands.utils;

import java.util.List;
import java.util.Random;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class RandomGenerator extends CommandBase {

	public RandomGenerator() {
		super("RandomGenerator", "Generate a random number between two values", "<Min> <Max>");
		this.addAlias("rng");
		this.addAlias("random");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(2) && args.isInteger(0) && args.isInteger(1)) {
			int min = Integer.min(args.getInteger(0), args.getInteger(1));
			int max = Integer.max(args.getInteger(0), args.getInteger(1));
			
			Random rnd = new Random();
			int random = rnd.nextInt((max - min) + 1) + min;
			executor.sendGitMessage("Your random number§7: §6" + random);
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

}
