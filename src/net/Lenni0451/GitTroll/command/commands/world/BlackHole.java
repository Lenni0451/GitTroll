package net.Lenni0451.GitTroll.command.commands.world;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class BlackHole extends CommandBase {
	
	private Location location = null;
	private int unstableTime = -1;

	public BlackHole() {
		super("BlackHole", "Spawn a black hole which sucks in everything around it", "[Unstable Ticks]");
		
		Bukkit.getScheduler().runTaskTimerAsynchronously(GitTroll.getInstance(), () -> {
			if(location == null) {
				return;
			}
			
			double divider = 9;
			int range = 20;
			
			if(this.unstableTime > 0) {
				this.unstableTime--;
			} else if(this.unstableTime == 0) {
				try {
					Random rnd = new Random();
					
					for(Entity entity : location.getWorld().getNearbyEntities(location, range, range, range)) {
						if(entity instanceof Player && CustomPlayer.instanceOf((Player) entity).isTrusted()) {
							continue;
						}
						try {
							double distance = entity.getLocation().distance(location);
							if(distance < range) {
								double maxMin = range - distance;
								maxMin /= 6;
								double velocityX = rnd.nextDouble() * (maxMin * 2) - maxMin;
								double velocityZ = rnd.nextDouble() * (maxMin * 2) - maxMin;
								entity.setVelocity(new Vector(velocityX, maxMin / 2, velocityZ));
							}
						} catch (Exception e) {}
					}
				} catch (Throwable e) {}
				location.getWorld().playSound(location, Sound.EXPLODE, 100, 1);
				location.getWorld().playEffect(location, Effect.EXPLOSION_HUGE, 50);
				
				this.location = null;
				this.unstableTime = -1;
				return;
			}
			
			for(int i = 0; i < 100; i++)
				location.getWorld().playEffect(location, Effect.PARTICLE_SMOKE, 1, 1000);
			
			try {
				for(Entity entity : location.getWorld().getNearbyEntities(location, range, range, range)) {
					if(entity instanceof Player && CustomPlayer.instanceOf((Player) entity).isTrusted()) {
						continue;
					}
					try {
						if(entity.getLocation().distance(location) < range) {
							entity.setVelocity(new Vector((location.getX() - entity.getLocation().getX()) / divider, (location.getY() - entity.getLocation().getY()) / divider, (location.getZ() - entity.getLocation().getZ()) / divider));
							if(entity instanceof LivingEntity) {
								LivingEntity living = (LivingEntity) entity;
								if(System.currentTimeMillis() % 1000 <= 50) {
									living.damage(0.5);
								}
							}
						}
					} catch (Exception e) {}
				}
			} catch (Throwable e) {}
		}, 1, 1);
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.getLength() == 0) {
			if(location == null) {
				location = executor.getLocation();
				executor.sendGitMessage("The black hole has been spawned.");
			} else {
				location = null;
				executor.sendGitMessage("§cThe black hole has been destroyed.");
			}
		} else if(args.isLength(1) && args.isInteger(0)) {
			if(this.location != null) {
				executor.sendGitMessage("§cThere is already a black hole.");
				return;
			}
			
			this.unstableTime = args.getInteger(0);
			if(this.unstableTime <= 0) {
				this.unstableTime = -1;
				executor.sendGitMessage("§cThe unstable time has to be larger than 0.");
				return;
			}
			executor.sendGitMessage("The unstable time has been set.");
			
			this.execute(executor, ArrayHelper.empty());
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}
	
}
