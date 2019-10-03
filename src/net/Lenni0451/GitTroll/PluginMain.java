package net.Lenni0451.GitTroll;

import org.bukkit.plugin.java.JavaPlugin;

public class PluginMain extends JavaPlugin {
	
	private final GitTroll gitTroll;
	
	public PluginMain() {
		this.gitTroll = new GitTroll(this);
	}
	
	@Override
	public void onEnable() {
		this.gitTroll.onEnable();
	}
	
	@Override
	public void onDisable() {
		this.gitTroll.onDisable();
	}
	
}
