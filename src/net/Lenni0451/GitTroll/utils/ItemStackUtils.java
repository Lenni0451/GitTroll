package net.Lenni0451.GitTroll.utils;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.MojangsonParser;

public class ItemStackUtils {

	public static ItemStack generateNew(final Material material) {
		return generateNew(material, 1);
	}
	
	public static ItemStack generateNew(final Material material, final int amount) {
		return generateNew(material, amount, 0);
	}
	
	public static ItemStack generateNew(final Material material, final int amount, final int damage) {
		return generateNew(material, amount, (short) damage);
	}
	
	public static ItemStack generateNew(final Material material, final int amount, final short damage) {
		return new ItemStack(material, amount, damage);
	}

	
	public static ItemStack generateNew(final Material material, final String name) {
		return generateNew(material, name, 1);
	}
	
	public static ItemStack generateNew(final Material material, final String name, final int count) {
		return generateNew(material, name, count, 0);
	}
	
	public static ItemStack generateNew(final Material material, final String name, final int count, final int damage) {
		return generateNew(material, name, count, (short) damage);
	}
	
	public static ItemStack generateNew(final Material material, final String name, final int count, final short damage) {
		ItemStack stack = generateNew(material, count, damage);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(name);
		stack.setItemMeta(meta);
		return stack;
	}
	
	public static ItemStack generateStackFromString(final String string) throws Exception {
		Item itm = null;
		int count = 1;
		short damage = 0;
		String tags = "";
		
		String[] args = string.split(" ");
		if(NumberUtils.isInteger(args[0])) {
			Item.REGISTRY.a(Integer.valueOf(args[0]));
		} else {
			MinecraftKey key = new MinecraftKey(args[0]);
			itm = Item.REGISTRY.get(key);
		}
		if(itm == null) {
			throw new Exception("The Item could not be found");
		}
		try {
			if(args.length >= 2) {
				count = Integer.valueOf(args[1]);
			}
		} catch (Exception e) {
			throw new Exception("The item count is not a number");
		}
		try {
			if(args.length >= 3) {
				damage = Short.valueOf(args[2]);
			}
		} catch (Exception e) {
			throw new Exception("The item damage is not a number");
		}
		try {
			if(args.length >= 4) {
				tags = arrayToString(args, 3);
			}
		} catch (Exception e) {
			throw new Exception("The item damage is not a number");
		}
		
		net.minecraft.server.v1_8_R3.ItemStack stack = new net.minecraft.server.v1_8_R3.ItemStack(itm, count, damage);
		if(!tags.isEmpty() && !tags.equals("{}")) {
			stack.setTag(MojangsonParser.parse(tags));
		}
		return CraftItemStack.asBukkitCopy(stack);
	}
	
	
	private static String arrayToString(final String[] array, final int start) {
		String out = "";
		for(int i = start; i < array.length; i++) {
			if(out.isEmpty()) {
				out = array[i];
			} else {
				out += " " + array[i];
			}
		}
		return out;
	}
	
}
