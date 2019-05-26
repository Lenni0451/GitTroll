package net.Lenni0451.GitTroll.command.commands.player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Freeze extends CommandBase implements Listener {
	
	List<CustomPlayer> frozenPlayers = new ArrayList<>();

	public Freeze() {
		super("Freeze", "Freeze a player at the position he currently is", "<Player>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			if(this.frozenPlayers.remove(vic)) {
				executor.sendGitMessage("§cThe player has been unfrozen.");
			} else {
				this.frozenPlayers.add(vic);
				executor.sendGitMessage("The player has been frozen.");
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
	public void onMove(PlayerMoveEvent event) {
		Location from = event.getFrom();
		Location to = event.getTo();
		
		if(from.getX() != to.getX() || from.getY() != to.getY() || from.getZ() != to.getZ())
			if(this.frozenPlayers.contains(CustomPlayer.instanceOf(event.getPlayer())))
				event.getPlayer().teleport(event.getFrom());
	}

}
