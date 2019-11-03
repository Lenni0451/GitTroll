package net.Lenni0451.GitTroll.utils;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class LocationUtils {
	
	private static final DecimalFormat decimalFormat = new DecimalFormat();
	
	static {
		decimalFormat.setMaximumFractionDigits(5);
		decimalFormat.setGroupingUsed(false);
	}
	
	public static Location getHighestPoint(final Location location) {
		Location tempLoc = location.clone();
		while(tempLoc.getBlockY() >= 0) {
			if(!tempLoc.getBlock().getType().equals(Material.AIR)) return tempLoc;
			tempLoc.subtract(0, 1, 0);
		}
		tempLoc = location.clone();
		while(tempLoc.getBlockY() <= 255) {
			if(!tempLoc.getBlock().getType().equals(Material.AIR)) return tempLoc;
			tempLoc.add(0, 1, 0);
		}
		return null;
	}
	
	public static Location getHighestBlockInWorld(final Location location) {
		Location newLoc = location.clone();
		newLoc.setY(255);
		while(newLoc.getBlockY() >= 0) {
			if(!newLoc.getBlock().getType().equals(Material.AIR)) return newLoc;
			newLoc.subtract(0, 1, 0);
		}
		return null;
	}
	
	public static String serialize(final Location location) {
		return location.getWorld().getName() + " " + decimalFormat.format(location.getX()).replace(",", ".") + " " + decimalFormat.format(location.getY()).replace(",", ".") + " " + decimalFormat.format(location.getZ()).replace(",", ".");
	}
	
	public static Location deserialize(final String string) {
		String[] parts = string.split(" ");
		return new Location(Bukkit.getWorld(parts[0]), Double.valueOf(parts[1]), Double.valueOf(parts[2]), Double.valueOf(parts[3]));
	}
	
}
