package net.Lenni0451.GitTroll.command.commands.utils;

import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.google.common.collect.Maps;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Bind extends CommandBase implements Listener {
	
	Map<CustomPlayer, Map<Material, String>> binds = Maps.newHashMap();

	public Bind() {
		super("Bind", "Bind a message/command to an item", "<Message/Command>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(executor.getPlayer().getInventory().getItemInHand() == null || executor.getPlayer().getInventory().getItemInHand().getType().equals(Material.AIR)) {
			executor.sendGitMessage("§cYou do not have an Item in your hand.");
			return;
		}
		
		if(args.isLarger(0)) {
			String message = args.getAsString(" ");
			
			Map<Material, String> messages = binds.get(executor);
			if(messages == null) {
				messages = Maps.newHashMap();
				this.binds.put(executor, messages);
			}
			
			messages.put(executor.getPlayer().getInventory().getItemInHand().getType(), message);
			executor.sendGitMessage("The message/command has been bound to the tool.");
		} else {
			Map<Material, String> messages = binds.get(executor);
			if(messages == null || !messages.containsKey(executor.getPlayer().getInventory().getItemInHand().getType())) {
				executor.sendGitMessage("§cYou do not have a message/command bound to this item.");
				return;
			}
			
			messages.remove(executor.getPlayer().getInventory().getItemInHand().getType());
			executor.sendGitMessage("The message/command has been unbound.");
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(event.getItem() == null || event.getItem().getType().equals(Material.AIR)) {
			return;
		}
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_AIR)) {
			CustomPlayer customPlayer = CustomPlayer.instanceOf(event.getPlayer());

			Map<Material, String> messages = this.binds.get(customPlayer);
			if(messages != null && messages.containsKey(event.getItem().getType())) {
				customPlayer.sudo(messages.get(event.getItem().getType()));
			}
		}
	}

}
