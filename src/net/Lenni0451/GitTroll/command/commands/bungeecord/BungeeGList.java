package net.Lenni0451.GitTroll.command.commands.bungeecord;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.io.ByteArrayDataInput;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.event.EventListener;
import net.Lenni0451.GitTroll.event.events.EventPluginMessage;
import net.Lenni0451.GitTroll.event.types.Event;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.BungeeUtils;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class BungeeGList extends CommandBase implements EventListener {
	
	List<CustomPlayer> waitingPlayers = new ArrayList<>();

	public BungeeGList() {
		super("BungeeGList", "Get a list of all servers and players on each of them");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			waitingPlayers.add(executor);
			
			BungeeUtils bu = new BungeeUtils(executor.getPlayer());
			bu.sendBungeeMessageToPlayer("GetServers");
			
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
				if(!waitingPlayers.contains(player)) {
					return;
				}

				String cmd = in.readUTF();
				String response = in.readUTF();
				
				if(cmd.equalsIgnoreCase("GetServers")) {
					BungeeUtils bu = new BungeeUtils(((EventPluginMessage) event).getPlayer());
					for(String server : response.split(", ")) {
						bu.sendBungeeMessageToPlayer("PlayerList", server);
					}
				} else if(cmd.equalsIgnoreCase("PlayerList")) {
					String server = response;
					String playerList = in.readUTF();
					
					player.sendMessage("§a" + server + " §e(" + (StringUtils.isEmpty(playerList)?"0":(playerList.split(", ").length)) + ")§7: §6" + playerList.replace(", ", "§7, §6"));
				}
			}
		}
	}

}
