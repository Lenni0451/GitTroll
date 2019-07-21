package net.Lenni0451.GitTroll.command.commands.bungeecord;

import java.util.ArrayList;
import java.util.List;

import com.google.common.io.ByteArrayDataInput;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.event.EventListener;
import net.Lenni0451.GitTroll.event.events.EventPluginMessage;
import net.Lenni0451.GitTroll.event.types.Event;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.BungeeUtils;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class BungeePlayerList extends CommandBase implements EventListener {
	
	List<CustomPlayer> waitingPlayers = new ArrayList<>();

	public BungeePlayerList() {
		super("BungeePlayerList", "Get a list of all online players on a bungee cord server", "<Server>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			waitingPlayers.add(executor);
			
			BungeeUtils bu = new BungeeUtils(executor.getPlayer());
			bu.sendBungeeMessageToPlayer("PlayerList", args.getString(0));
			
	        executor.sendGitMessage("The message has been sent to the bungee.");
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}
	
	@Override
	public void onEvent(Event event) {
		if(event instanceof EventPluginMessage) {
			if(((EventPluginMessage) event).getChannel().equals("BungeeCord")) {
				ByteArrayDataInput in = ((EventPluginMessage) event).getDataStream();

				CustomPlayer player = CustomPlayer.instanceOf(((EventPluginMessage) event).getPlayer());
				if(!waitingPlayers.remove(player)) {
					return;
				}

				try {
					in.readUTF();
					in.readUTF();
					String playerList = in.readUTF().replace(", ", "§7, §6");
					int playerCount = playerList.split("§7, §6").length;
					
					player.sendGitMessage("Online players §7(" + playerCount + ")§a: §6" + playerList);
				} catch (Exception e) {
					player.sendGitMessage("§cThe server could not be found.");
				}
			}
		}
	}

}
