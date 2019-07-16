package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitTask;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.Lenni0451.GitTroll.utils.ItemStackUtils;

public class JumpScare extends CommandBase {
	
	public JumpScare() {
		super("JumpScare", "Scare a player", "<Player>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			Location spawnLocation = vic.getLocation().clone();
			for(int i = 0; i < 5; i++) spawnLocation = spawnLocation.add(vic.getLocation().getDirection().getX(), 0, vic.getLocation().getDirection().getZ());
			spawnLocation.setYaw(vic.getLocation().getYaw() + 180);
			
			ArmorStand armorStand = (ArmorStand) vic.getWorld().spawnEntity(spawnLocation, EntityType.ARMOR_STAND);
			armorStand.setBasePlate(false);
			armorStand.setArms(true);
			armorStand.setGravity(false);
			{//Boots
				ItemStack stack = ItemStackUtils.generateNew(Material.LEATHER_BOOTS);
				LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
				meta.setColor(Color.BLACK);
				stack.setItemMeta(meta);
				armorStand.setBoots(stack);
			}
			{//Leggings
				ItemStack stack = ItemStackUtils.generateNew(Material.LEATHER_LEGGINGS);
				LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
				meta.setColor(Color.BLACK);
				stack.setItemMeta(meta);
				armorStand.setLeggings(stack);
			}
			{//Chestplate
				ItemStack stack = ItemStackUtils.generateNew(Material.LEATHER_CHESTPLATE);
				LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
				meta.setColor(Color.BLACK);
				stack.setItemMeta(meta);
				armorStand.setChestplate(stack);
			}
			{//Head
				ItemStack stack = ItemStackUtils.generateNew(Material.SKULL_ITEM, 1, 1);
				armorStand.setHelmet(stack);
			}
			{//Hand
				ItemStack stack = ItemStackUtils.generateNew(Material.GOLD_SWORD);
				armorStand.setItemInHand(stack);
			}
			BukkitTask task = Bukkit.getScheduler().runTaskTimer(GitTroll.getInstance(), () -> {
				double divider = 10;
				
				double posX = (armorStand.getLocation().getX() - vic.getLocation().getX()) / divider;
				double posY = (armorStand.getLocation().getY() - vic.getLocation().getY()) / divider;
				double posZ = (armorStand.getLocation().getZ() - vic.getLocation().getZ()) / divider;
				
				for(int i = 0; i < 20; i++) {
					armorStand.teleport(armorStand.getLocation().subtract(posX, posY, posZ));
				}
				
				armorStand.getWorld().playSound(vic.getLocation(), Sound.GHAST_SCREAM, 1, 1);
			}, 0, 1);
			Bukkit.getScheduler().runTaskLater(GitTroll.getInstance(), () -> {
				task.cancel();
				armorStand.remove();
			}, 10);
			
			executor.sendGitMessage("The player gets scared now.");
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
