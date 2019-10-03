package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;

public class FakeHacker extends CommandBase {

	List<CustomPlayer> player = new ArrayList<>();
	
	public FakeHacker() {
		super("FakeHacker", "Let all entities in the world have hacks (for a player)", "<Player>");
		
		Bukkit.getScheduler().runTaskTimerAsynchronously(GitTroll.getInstance().getParentPlugin(), () -> {
			try {
				Random rnd = new Random();
				
				for(CustomPlayer player : this.player) {
					try {
						World tw = player.getLocation().getWorld();
						for (LivingEntity entity : tw.getLivingEntities()) {
							int eid = entity.getEntityId();
							if (eid != player.getPlayer().getEntityId()) {
								int min = Byte.MIN_VALUE;
								int max = Byte.MAX_VALUE;
								
								byte b1 = Float.valueOf((rnd.nextFloat() * (max - min)) + min).byteValue();
								byte b2 = Float.valueOf((rnd.nextFloat() * (max - min)) + min).byteValue();
								
								b1 = ((byte) (int) (b1 * 256.0F / 360.0F));
								b2 = ((byte) (int) (b2 * 256.0F / 360.0F));
								player.sendPacket(new PacketPlayOutEntityTeleport(eid, entity.getLocation().getBlockX() * 32, entity.getLocation().getBlockY() * 32, entity.getLocation().getBlockZ() * 32, b1, b2, true));
							}
						}
					} catch (Throwable e) {}
				}
			} catch (Throwable e) {}
		}, 1, 1);
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			if(player.remove(vic)) {
				executor.sendGitMessage("§cThe entities now don't have hacks anymore.");
			} else {
				player.add(vic);
				executor.sendGitMessage("The entities now have hacks.");
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

}
