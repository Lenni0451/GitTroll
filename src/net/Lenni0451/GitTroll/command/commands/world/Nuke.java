package net.Lenni0451.GitTroll.command.commands.world;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Nuke extends CommandBase {
	
	private final float velocity = 1.F;

	public Nuke() {
		super("Nuke", "Spawn a nuke which splits after colliding");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			FallingBlock nukeBlock = executor.getWorld().spawnFallingBlock(executor.getLocation().add(0, 10, 0), Material.WOOL, (byte) 15);
			
			BukkitTask[] task = new BukkitTask[5];
			task[0] = Bukkit.getScheduler().runTaskTimer(GitTroll.getInstance().getParentPlugin(), () -> {
				if(!nukeBlock.isValid()) {
					Location loc = nukeBlock.getLocation();
					loc.getWorld().createExplosion(loc, 50);
					task[0].cancel();
					
					for(int i = 0; i < 4; i++) {
						final int fi = i;
						
						FallingBlock splittedBlock = loc.getWorld().spawnFallingBlock(loc, Material.WOOL, (byte) 15);
						if(i == 0) {
							splittedBlock.setVelocity(new Vector(-velocity, velocity + 0.5F, -velocity));
						} else if(i == 1) {
							splittedBlock.setVelocity(new Vector(velocity, velocity + 0.5F, -velocity));
						} else if(i == 2) {
							splittedBlock.setVelocity(new Vector(-velocity, velocity + 0.5F, velocity));
						} else if(i == 3) {
							splittedBlock.setVelocity(new Vector(velocity, velocity + 0.5F, velocity));
						}
						task[i + 1] = Bukkit.getScheduler().runTaskTimer(GitTroll.getInstance().getParentPlugin(), () -> {
							if(!splittedBlock.isValid()) {
								Location locc = splittedBlock.getLocation();
								locc.getWorld().createExplosion(locc, 50);
								task[fi + 1].cancel();
							}
						}, 1, 1);
					}
				}
			}, 1, 1);
			executor.sendGitMessage("A nuke has been dropped.");
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}
	
}
