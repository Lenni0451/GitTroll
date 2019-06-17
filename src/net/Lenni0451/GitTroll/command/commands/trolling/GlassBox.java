package net.Lenni0451.GitTroll.command.commands.trolling;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class GlassBox extends CommandBase {

	private List<CustomPlayer> players = new ArrayList<>();
	
	public GlassBox() {
		super("GlassBox", "Trap a player inside a clientside glassbox", "<Player>");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isLength(1)) {
			CustomPlayer vic = this.parsePlayer(args.getString(0), executor);
			
			int range = 5;
            for(int i = -range; i <= range; i++) {
                for(int i2 = -range; i2 <= range; i2++) {
                    for(int i3 = -range; i3 <= range; i3++) {
                        vic.getPlayer().sendBlockChange(new Location(vic.getLocation().getWorld(), vic.getLocation().getX() + i, vic.getLocation().getY() + i2, vic.getLocation().getZ() + i3), Material.GLASS, (byte)0);
                    }
                }
            }
            executor.sendGitMessage("The player has been trapped inside a glassbox.");
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
