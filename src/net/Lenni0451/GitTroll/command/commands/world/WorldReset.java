package net.Lenni0451.GitTroll.command.commands.world;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.Lenni0451.GitTroll.utils.Logger;

public class WorldReset extends CommandBase {
	
	private final AtomicBoolean isBeeingResetted = new AtomicBoolean();

	public WorldReset() {
		super("WorldReset", "Reset the world");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(0)) {
			if(!this.isBeeingResetted.compareAndSet(false, true)) {
				executor.sendGitMessage("The world is already beeing resetted.");
				return;
			}
			Logger.broadcastGitMessage("The player §6" + executor.getPlayer().getName() + " §ais resetting the world.");
			Bukkit.getScheduler().runTask(GitTroll.getInstance(), () -> {
				DecimalFormat format = new DecimalFormat();
				format.setMinimumFractionDigits(0);
				format.setMaximumFractionDigits(0);
				long totalStartTime = System.currentTimeMillis();
				
				for(World world : Bukkit.getWorlds()) {
					if(world.getPlayers().isEmpty()) {
						continue;
					}
					long startTime = System.currentTimeMillis();
					for(Chunk chunk : world.getLoadedChunks()) {
						chunk.unload();
						world.regenerateChunk(chunk.getX(), chunk.getZ());
						world.refreshChunk(chunk.getX(), chunk.getZ());
						chunk.load();
					}
					executor.sendGitMessage("Resetted world §6" + world.getName() + " §ain §6" + format.format(System.currentTimeMillis() - startTime) + "ms");
				}
				
				Logger.broadcastGitMessage("Successfully resetted worlds in §6" + format.format(System.currentTimeMillis() - totalStartTime) + "ms");
			});
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

}
