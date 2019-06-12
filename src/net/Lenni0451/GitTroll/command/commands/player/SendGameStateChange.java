package net.Lenni0451.GitTroll.command.commands.player;

import java.util.List;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutGameStateChange;

public class SendGameStateChange extends CommandBase {

	public SendGameStateChange() {
		super("SendGameStateChange", "Send a game state change to a player", "<Player> <Reason> <Value>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(3) && args.isInteger(1) && args.isFloat(2)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			vic.sendPacket(new PacketPlayOutGameStateChange(args.getInteger(1), args.getFloat(2)));
			executor.sendGitMessage("A game state packet has been sent to the player.");
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {
		if(args.isEmpty()) {
			this.tabCompletePlayers(tabComplete);
		} else if(args.isLength(1)) {
			for(int i = 0; i <= 10; i++) {
				tabComplete.add(String.valueOf(i));
			}
		} else if(args.isLength(2) && args.isInteger(1)) {
			switch (args.getInteger(1)) {
			case 3:
				tabComplete.add("0");
				tabComplete.add("1");
				tabComplete.add("2");
				tabComplete.add("3");
				break;
				
			case 4:
				tabComplete.add("0");
				tabComplete.add("1");
				break;
				
			case 5:
				tabComplete.add("0");
				tabComplete.add("101");
				tabComplete.add("102");
				tabComplete.add("103");
				break;
				
			case 7:
				tabComplete.add("0");
				tabComplete.add("1");
				break;

			default:
				tabComplete.add("0");
				break;
			}
		}
	}

}
