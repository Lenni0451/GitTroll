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

public class BungeePlayerCount extends CommandBase implements EventListener {
	
	List<CustomPlayer> waitingPlayers = new ArrayList<>();

	public BungeePlayerCount() {
		super("BungeePlayerCount", "Get the player count on a bungee subserver", "<Server Name>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			String server = args.getString(0);
			
			waitingPlayers.add(executor);
			
			BungeeUtils bu = new BungeeUtils(executor.getPlayer());
			bu.sendBungeeMessageToPlayer("PlayerCount", server);
			
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
					String server = in.readUTF();
					int players = in.readInt();
					
					player.sendGitMessage("On the server §6" + server + " §aare §6" + players + " §aplayers.");
				} catch (Exception e) {
					player.sendGitMessage("§cThe server could not be found.");
				}
			}
		}
	}

}
