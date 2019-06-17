package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutGameStateChange;

public class BlackScreen extends CommandBase implements Listener {

	private List<CustomPlayer> players = new ArrayList<>();
	
	public BlackScreen() {
		super("BlackScreen", "Let a player see a blackscreen", "<Player>");
		
		Bukkit.getScheduler().runTaskTimer(GitTroll.getInstance(), () -> {
			try {
				for(CustomPlayer player : this.players) {
					player.sendPacket(new PacketPlayOutGameStateChange(4, 0));
				}
			} catch (Throwable e) {}
		}, 1, 1);
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			if(players.remove(vic)) {
				executor.sendGitMessage("§cThe player no longer has a blackscreen.");
			} else {
				this.players.add(vic);
				executor.sendGitMessage("The player now has a blackscreen.");
			}
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
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		this.players.remove(CustomPlayer.instanceOf(event.getPlayer()));
	}

}
