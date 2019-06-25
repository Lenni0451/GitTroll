package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.event.EventListener;
import net.Lenni0451.GitTroll.event.events.EventServerPacket;
import net.Lenni0451.GitTroll.event.types.Event;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutMap;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunkBulk;
import net.minecraft.server.v1_8_R3.PacketPlayOutMultiBlockChange;

public class BlockChunkUpdates extends CommandBase implements Listener, EventListener {

	private List<CustomPlayer> players = new ArrayList<>();
	
	public BlockChunkUpdates() {
		super("BlockChunkUpdates", "Block the chunk loading packet for a player", "<Player>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			if(this.players.remove(vic)) {
				executor.sendGitMessage("§cThe player now can receive chunk updates again.");
			} else {
				this.players.add(vic);
				executor.sendGitMessage("The player now does not receive chunk updates.");
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

	@Override
	public void onEvent(Event event) {
		if(event instanceof EventServerPacket) {
			if(!this.players.contains(CustomPlayer.instanceOf(((EventServerPacket) event).getPlayer()))) {
				return;
			}
			Packet<?> packet = ((EventServerPacket) event).getPacket();
			if(packet instanceof PacketPlayOutMap || packet instanceof PacketPlayOutMapChunkBulk || packet instanceof PacketPlayOutMapChunk || packet instanceof PacketPlayOutMultiBlockChange || packet instanceof PacketPlayOutBlockChange) {
				((EventServerPacket) event).setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		this.players.remove(CustomPlayer.instanceOf(event.getPlayer()));
	}
	
}
