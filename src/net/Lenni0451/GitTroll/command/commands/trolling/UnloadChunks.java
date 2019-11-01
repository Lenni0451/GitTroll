package net.Lenni0451.GitTroll.command.commands.trolling;

import java.lang.reflect.Field;
import java.util.List;

import org.bukkit.Chunk;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk;

public class UnloadChunks extends CommandBase {

	public UnloadChunks() {
		super("UnloadChunks", "Unload the chunks around a player", "<Player>");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			Chunk currentChunk = vic.getWorld().getChunkAt(vic.getLocation());
			int chunkX = currentChunk.getX();
			int chunkZ = currentChunk.getZ();
			int range = 15;
			for(int x = -range; x <= range; x++) {
				for(int z = -range; z <= range; z++) {
					int nX = chunkX + x;
					int nZ = chunkZ + z;
					
					PacketPlayOutMapChunk chunkPacket = new PacketPlayOutMapChunk();
					
					try {
						Field f = chunkPacket.getClass().getDeclaredField("a");
						f.setAccessible(true);
						f.setInt(chunkPacket, nX);

						f = chunkPacket.getClass().getDeclaredField("b");
						f.setAccessible(true);
						f.setInt(chunkPacket, nZ);
						
						PacketPlayOutMapChunk.ChunkMap mapChunk = new PacketPlayOutMapChunk.ChunkMap();
						mapChunk.a = new byte[0];
						mapChunk.b = 0;
						f = chunkPacket.getClass().getDeclaredField("c");
						f.setAccessible(true);
						f.set(chunkPacket, mapChunk);
						
						f = chunkPacket.getClass().getDeclaredField("d");
						f.setAccessible(true);
						f.set(chunkPacket, true);
					} catch (Throwable e) {}
					vic.sendPacket(chunkPacket);
				}
			}
			executor.sendGitMessage("All chunks around the player have been unloaded");
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {
		if(args.isEmpty()) {
			this.tabCompletePlayers(tabComplete);
		}
	}
	
}
