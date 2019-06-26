package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class InteractTroll extends CommandBase implements Listener {
	
	Map<String, TrollType> trollBlocks = new HashMap<>();

	public InteractTroll() {
		super("InteractTroll", "Troll a player when he interacts with a block", "<Troll>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			TrollType type = null;
			for(TrollType trollType : TrollType.values()) {
				if(trollType.toString().equalsIgnoreCase(args.getString(0))) {
					type = trollType;
				}
			}
			if(type == null) {
				executor.sendGitMessage("§cThe troll type could not be found.");
				return;
			}
			
			Block lookingAt = executor.getLooking();
			if(lookingAt == null || lookingAt.getType().equals(Material.AIR)) {
				executor.sendGitMessage("§cYou have to look at the block you want to rig.");
				return;
			}
			
			trollBlocks.put(lookingAt.getLocation().toString(), type);
			executor.sendGitMessage("The troll block has been set up.");
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {
		if(args.isEmpty()) {
			for(TrollType type : TrollType.values()) {
				tabComplete.add(type.toString().toLowerCase());
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		CustomPlayer player = CustomPlayer.instanceOf(event.getPlayer());
		if(player.isTrusted() || (!event.getAction().equals(Action.LEFT_CLICK_BLOCK) && !event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			return;
		}
		
		Location loc = event.getClickedBlock().getLocation();
		String location = loc.toString();
		TrollType type = this.trollBlocks.get(location);
		if(type != null) {
			this.trollBlocks.remove(location);
			
			switch (type) {
			case KILL:
				player.getPlayer().setHealth(0);
				break;
				
			case EXPLODE:
				loc.getWorld().createExplosion(loc, 5);
				loc.getBlock().setType(Material.AIR);
				break;
				
			case LAVA:
				loc.getBlock().setType(Material.LAVA);
				break;
				
			case ANVIL:
				loc = player.getLocation();
				loc.add(0, 1, 0);
				int targetHeight = Math.min(loc.getBlockY() + 30, loc.getWorld().getMaxHeight());
				while(loc.getBlockY() < targetHeight) {
					if(!loc.getBlock().getType().equals(Material.AIR))
						loc.getBlock().setType(Material.AIR);
					
					loc.add(0, 1, 0);
				}
				loc.add(0, -1, 0);
				loc.getBlock().setType(Material.ANVIL);
				break;

			default:
				break;
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		CustomPlayer player = CustomPlayer.instanceOf(event.getPlayer());
		if(player.isTrusted()) {
			if(this.trollBlocks.containsKey(event.getBlock().getLocation().toString())) {
				this.trollBlocks.remove(event.getBlock().getLocation().toString());
				player.sendGitMessage("The troll block has been removed.");
			}
		}
	}

}

enum TrollType {
	
	EXPLODE, KILL, ANVIL, LAVA
	
}