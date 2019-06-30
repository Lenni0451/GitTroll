package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.Sound;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutGameStateChange;

public class JumpScare extends CommandBase {

	private Map<CustomPlayer, AtomicInteger> players = new HashMap<>();
	private Random rnd = new Random();
	
	public JumpScare() {
		super("JumpScare", "Scare a player", "<Player>");
		
		Bukkit.getScheduler().runTaskTimer(GitTroll.getInstance(), () -> {
			try {
				Iterator<Map.Entry<CustomPlayer, AtomicInteger>> iterator = this.players.entrySet().iterator();
				while(iterator.hasNext()) {
					Map.Entry<CustomPlayer, AtomicInteger> entry = iterator.next();
					entry.getKey().sendPacket(new PacketPlayOutGameStateChange(10, 0));
					entry.getKey().playSound(Sound.values()[rnd.nextInt(Sound.values().length)]);
					if(entry.getValue().addAndGet(-1) <= 0) {
						iterator.remove();
					}
				}
			} catch (Throwable e) {}
		}, 1, 1);
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			if(this.players.containsKey(vic)) {
				this.players.remove(vic);
				executor.sendGitMessage("§cThe player is no longer scared.");
			} else {
				this.players.put(vic, new AtomicInteger(5 * 20));
				executor.sendGitMessage("The player is now scared.");
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
