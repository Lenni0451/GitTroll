package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.List;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.minecraft.server.v1_8_R3.EnumDifficulty;
import net.minecraft.server.v1_8_R3.PacketPlayOutRespawn;
import net.minecraft.server.v1_8_R3.WorldType;
import net.minecraft.server.v1_8_R3.WorldSettings.EnumGamemode;

public class FakeEnd extends CommandBase {

	public FakeEnd() {
		super("FakeEnd", "Send the player to a fake end dimension without ground", "<Player>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			vic.sendPacket(new PacketPlayOutRespawn(1, EnumDifficulty.PEACEFUL, WorldType.NORMAL, EnumGamemode.NOT_SET));
			executor.sendGitMessage("The player is now stuck in an fake end.");
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
