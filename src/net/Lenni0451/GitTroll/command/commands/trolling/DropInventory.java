package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class DropInventory extends CommandBase {

	public DropInventory() {
		super("DropInventory", "Drop the inventory items of a player", "<Player>");
		
		this.addAlias("dropinv");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			for(ItemStack stack : vic.getPlayer().getInventory().getContents()) {
				if(stack == null || stack.getType().equals(Material.AIR)) {
					continue;
				}
				Item item = vic.getPlayer().getWorld().dropItemNaturally(vic.getPlayer().getLocation().add(0, 1, 0), stack);
				item.setPickupDelay(40);
			}
			vic.getPlayer().getInventory().clear();
			executor.sendGitMessage("The inventory of the player has been dropped.");
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {
		if(args.getLength() == 0) {
			this.tabCompletePlayers(tabComplete);
		}
	}

}
