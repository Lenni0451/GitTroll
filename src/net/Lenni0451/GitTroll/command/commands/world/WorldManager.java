package net.Lenni0451.GitTroll.command.commands.world;

import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.Lenni0451.GitTroll.utils.ItemStackUtils;

public class WorldManager extends CommandBase implements Listener {

	public WorldManager() {
		super("WorldManager", "Manage the worlds on the server");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			List<World> worlds = Bukkit.getWorlds();
			
			int rows = (int) Math.ceil((double) worlds.size() / 9D);
			Inventory inv = Bukkit.createInventory(null, rows * 9, "§5World Manager");
			for(World world : worlds) {
				Environment environment = world.getEnvironment();
				Material mat;
				switch(environment) {
					default:
					case NORMAL:
						mat = Material.GRASS;
						break;
					case NETHER:
						mat = Material.NETHERRACK;
						break;
					case THE_END:
						mat = Material.ENDER_STONE;
				}
				ItemStack stack = ItemStackUtils.generateNew(mat, "§6" + world.getName());
				inv.addItem(stack);
			}
			
			executor.getPlayer().openInventory(inv);
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		CustomPlayer player = CustomPlayer.instanceOf((Player) event.getWhoClicked());
		ItemStack clickedItem = event.getCurrentItem();
		if(clickedItem == null || clickedItem.getType() == null) {
			return;
		}
		
		if(event.getInventory().getName().equalsIgnoreCase("§5World Manager") && player.isTrusted()) {
			event.setCancelled(true);
			
			if(clickedItem.getType().equals(Material.GRASS) || clickedItem.getType().equals(Material.NETHERRACK) || clickedItem.getType().equals(Material.ENDER_STONE)) {
				String worldName = clickedItem.getItemMeta().getDisplayName().replaceAll("§.", "");
				try {
					Bukkit.getWorld(worldName).getAllowAnimals();
				} catch (Throwable e) {
					return;
				}
				
				Inventory inv = Bukkit.createInventory(player.getPlayer(), 9, "§5" + worldName);
				inv.setItem(1, ItemStackUtils.generateNew(Material.ENDER_PEARL, "§6Teleport to"));
				inv.setItem(7, ItemStackUtils.generateNew(Material.BARRIER, "§cDelete world"));
				player.getPlayer().openInventory(inv);
			}
		} else if(clickedItem.getType().equals(Material.ENDER_PEARL) || clickedItem.getType().equals(Material.BARRIER)) {
			String worldName = event.getInventory().getTitle().replaceAll("§.", "");
			World world;
			try {
				world = Bukkit.getWorld(worldName);
				world.getAllowAnimals();
			} catch (Throwable e) {
				return;
			}
			event.setCancelled(true);
			
			if(clickedItem.getType().equals(Material.ENDER_PEARL)) {
				event.getWhoClicked().teleport(new Location(world, world.getSpawnLocation().getX(), world.getHighestBlockYAt(world.getSpawnLocation()) + 1, world.getSpawnLocation().getZ()));
				player.getPlayer().closeInventory();
				player.sendGitMessage("Teleported to the world.");
			} else {
				try {
					Bukkit.unloadWorld(world, true);
					FileUtils.deleteDirectory(world.getWorldFolder());
					player.sendGitMessage("Successfully deleted world.");
				} catch (Exception e) {
					player.sendGitMessage("§cError deleting world (Some files may got deleted).");
				}
			}
			player.getPlayer().closeInventory();
		}
	}

}
