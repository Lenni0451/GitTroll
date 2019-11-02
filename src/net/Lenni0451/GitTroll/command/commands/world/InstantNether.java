package net.Lenni0451.GitTroll.command.commands.world;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.Lenni0451.GitTroll.utils.LocationUtils;

public class InstantNether extends CommandBase {

	public InstantNether() {
		super("InstantNether", "Create a nether landscape around you");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			executor.sendGitMessage("Calculating a nether for you...");
			Bukkit.getScheduler().runTaskAsynchronously(GitTroll.getInstance().getParentPlugin(), () -> {
				final int range = 30;
				final Location startLocation = executor.getLocation().clone();
				final Random rnd = new Random();
				
				final Map<String, Material> blocksToDo = new HashMap<>();
				
				for(int x = -range; x <= range; x++) {
					for(int z = -range; z <= range; z++) {
						Location newLoc = startLocation.clone().add(x, 0, z);
						newLoc.setY(newLoc.getWorld().getHighestBlockYAt(newLoc));
						if(rnd.nextInt(101) >= 75) {
							if(rnd.nextInt(101) >= 50) {
								blocksToDo.put(LocationUtils.serialize(newLoc), Material.SOUL_SAND);
								if(rnd.nextInt(101) >= 50 && newLoc.getBlockY() <= 255) {
									blocksToDo.put(LocationUtils.serialize(newLoc.add(0, 1, 0)), Material.NETHER_WARTS);
								}
							} else {
								blocksToDo.put(LocationUtils.serialize(newLoc), Material.GRAVEL);
							}
						} else {
							blocksToDo.put(LocationUtils.serialize(newLoc), Material.NETHERRACK);
							if(rnd.nextInt(101) >= 90 && newLoc.getBlockY() <= 255) {
								blocksToDo.put(LocationUtils.serialize(newLoc.add(0, 1, 0)), Material.FIRE);
							}
						}
					}
				}
				if(rnd.nextInt(101) >= 50) {
					int randX = rnd.nextInt(range * 2) - range;
					int randZ = rnd.nextInt(range * 2) - range;
					Location newLoc = startLocation.clone().add(randX, 0, randZ);
					newLoc.setY(newLoc.getWorld().getHighestBlockYAt(newLoc));

					blocksToDo.put(LocationUtils.serialize(newLoc), Material.OBSIDIAN);
					blocksToDo.put(LocationUtils.serialize(newLoc.clone().add(0, 0, 1)), Material.OBSIDIAN);
					blocksToDo.put(LocationUtils.serialize(newLoc.clone().add(0, 0, 2)), Material.OBSIDIAN);
					blocksToDo.put(LocationUtils.serialize(newLoc.clone().add(0, 0, 3)), Material.OBSIDIAN);
					blocksToDo.put(LocationUtils.serialize(newLoc.clone().add(0, 1, 3)), Material.OBSIDIAN);
					blocksToDo.put(LocationUtils.serialize(newLoc.clone().add(0, 2, 3)), Material.OBSIDIAN);
					blocksToDo.put(LocationUtils.serialize(newLoc.clone().add(0, 3, 3)), Material.OBSIDIAN);
					blocksToDo.put(LocationUtils.serialize(newLoc.clone().add(0, 4, 3)), Material.OBSIDIAN);
					blocksToDo.put(LocationUtils.serialize(newLoc.clone().add(0, 4, 2)), Material.OBSIDIAN);
					blocksToDo.put(LocationUtils.serialize(newLoc.clone().add(0, 4, 1)), Material.OBSIDIAN);
					blocksToDo.put(LocationUtils.serialize(newLoc.clone().add(0, 4, 0)), Material.OBSIDIAN);
					blocksToDo.put(LocationUtils.serialize(newLoc.clone().add(0, 3, 0)), Material.OBSIDIAN);
					blocksToDo.put(LocationUtils.serialize(newLoc.clone().add(0, 2, 0)), Material.OBSIDIAN);
					blocksToDo.put(LocationUtils.serialize(newLoc.clone().add(0, 1, 0)), Material.OBSIDIAN);
					blocksToDo.put(LocationUtils.serialize(newLoc.clone().add(0, 0, 0)), Material.OBSIDIAN);
				}
				
				Bukkit.getScheduler().runTask(GitTroll.getInstance().getParentPlugin(), () -> {
					executor.sendGitMessage("Your nether has been calculated. Placing it now...");
					for(Map.Entry<String, Material> entry : blocksToDo.entrySet()) {
						LocationUtils.deserialize(entry.getKey()).getBlock().setType(entry.getValue());
					}
					
					for(int i = 0; i < rnd.nextInt(3); i++) {
						Location spawnLoc = startLocation.clone().add(0, 10 + rnd.nextInt(10), 0);
						spawnLoc.getWorld().spawnEntity(spawnLoc, EntityType.GHAST);
					}
					executor.sendGitMessage("Your nether has been placed!");
				});
			});
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

}
