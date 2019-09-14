package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.common.collect.Maps;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class ZoomSpam extends CommandBase {

	private Map<CustomPlayer, AtomicInteger> players = Maps.newHashMap();
	
	public ZoomSpam() {
		super("ZoomSpam", "ZoomSpamming a player", "<Player>");
		this.addAlias("ZoomSpamming");
		
		Bukkit.getScheduler().runTaskTimer(GitTroll.getInstance(), () -> {
			try {
				for(Map.Entry<CustomPlayer, AtomicInteger> entry : this.players.entrySet()) {
					if(entry.getValue().compareAndSet(1, 2)) {
						entry.getKey().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 2));
					} else {
						entry.getValue().set(1);
						entry.getKey().getPlayer().removePotionEffect(PotionEffectType.SLOW);
					}
				}
			} catch (Throwable e) {}
		}, 1, 1);
	}
	
	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			if(this.players.remove(vic) != null) {
				executor.sendGitMessage("The Player is no longer zoom spammed.");
			} else {
				this.players.put(vic, new AtomicInteger());
				executor.sendGitMessage("The Player is now zoom spammed.");
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
