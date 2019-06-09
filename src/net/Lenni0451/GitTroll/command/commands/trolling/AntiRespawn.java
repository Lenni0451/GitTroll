package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.List;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;

public class AntiRespawn extends CommandBase {

	public AntiRespawn() {
		super("AntiRespawn", "Let a player be stuck in the respawn screen (by RK_01)", "<Player>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			for(int i = -128; i < 126; i++) {
	            vic.sendPacket(new PacketPlayOutEntityStatus(vic.getCraftPlayer().getHandle(), (byte)i));
	        }
			executor.sendGitMessage("The player is now stuck in the respawn screen.");
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
