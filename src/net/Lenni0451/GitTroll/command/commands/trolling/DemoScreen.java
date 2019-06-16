package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.List;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutGameStateChange;

public class DemoScreen extends CommandBase {

	public DemoScreen() {
		super("DemoScreen", "Show the demo screen to a player", "<Player>");
		this.addAlias("Demo");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);

			vic.sendPacket(new PacketPlayOutGameStateChange(5, 0));
			vic.sendPacket(new PacketPlayOutGameStateChange(5, 101));
			vic.sendPacket(new PacketPlayOutGameStateChange(5, 102));
			vic.sendPacket(new PacketPlayOutGameStateChange(5, 103));
			executor.sendGitMessage("The demo screen is now shown for this player.");
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
