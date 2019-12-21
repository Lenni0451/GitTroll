package net.Lenni0451.GitTroll.command.commands.server;

import java.lang.reflect.Field;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.minecraft.server.v1_8_R3.PlayerList;

public class SetSlots extends CommandBase {

	public SetSlots() {
		super("SetSlots", "Set the server slots to a given value", "<Slot count>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) throws Throwable {
		if(args.isLength(1) && args.isInteger(0)) {
			Server server = Bukkit.getServer();
			if(!(server instanceof CraftServer)) {
				executor.sendGitMessage("§cCould not set slot count.");
				executor.sendGitMessage("§cThe Bukkit.getServer() method returned an invalid server type.");
				return;
			}
			
			CraftServer craftServer = (CraftServer) server;
			PlayerList playerList = craftServer.getHandle();
			Field maxPlayersField = PlayerList.class.getDeclaredField("maxPlayers");
			maxPlayersField.setAccessible(true);
			maxPlayersField.set(playerList, args.getInteger(0));
			executor.sendGitMessage("Successfully set the max player count.");
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

}
