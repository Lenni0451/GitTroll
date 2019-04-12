package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class DropHand extends CommandBase {

	public DropHand() {
		super("DropHand", "Drop the hand item of a player", "<Player>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			ItemStack itemInHand = vic.getPlayer().getInventory().getItemInHand();
			if(itemInHand == null || itemInHand.getType().equals(Material.AIR)) {
				executor.sendGitMessage("§cThe player does not have an item in his hand.");
				return;
			}
			
			Item item = vic.getPlayer().getWorld().dropItemNaturally(vic.getPlayer().getLocation().add(0, 1, 0), itemInHand);
			item.setPickupDelay(40);
			vic.getPlayer().getInventory().setItemInHand(null);
			executor.sendGitMessage("The held item of the player has been dropped.");
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
