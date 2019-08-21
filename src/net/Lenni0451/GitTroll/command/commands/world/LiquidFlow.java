package net.Lenni0451.GitTroll.command.commands.world;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;

import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class LiquidFlow extends CommandBase implements Listener {

	boolean flow = false;
	
	public LiquidFlow() {
		super("LiquidFlow", "Let liquids flow indefinitely");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			this.flow = !this.flow;
			if(this.flow) {
				executor.sendGitMessage("Liquids now flow indefinitely.");
			} else {
				executor.sendGitMessage("§cLiquids now don't flow indefinitely anymore.");
			}
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}
	
	@EventHandler
	public void onBlockPhysics(BlockPhysicsEvent event) {
		if(this.flow) {
			Block block = event.getBlock();
			if (block.isLiquid()) {
				block.setType(block.getType());
			}
		}
	}

	@EventHandler
	public void onBlockFromTo(BlockFromToEvent event) {
		if(this.flow) {
			Block block = event.getToBlock();
			if (block.isLiquid()) {
				block.setType(block.getType());
			}
		}
	}
	
}
