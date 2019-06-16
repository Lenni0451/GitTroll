package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.List;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutGameStateChange;

public class ElderGuardian extends CommandBase {

	public ElderGuardian() {
		super("ElderGuardian", "Show a guradian effect on the players screen", "<Player>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			vic.sendPacket(new PacketPlayOutGameStateChange(10, 0));
			executor.sendGitMessage("The effect has been displayed on the screen of the player.");
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {
		if(args.isEmpty()) {
			this.tabCompletePlayers(tabComplete);
		}
	}
	
}
