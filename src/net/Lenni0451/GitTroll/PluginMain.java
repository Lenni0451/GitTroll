package net.Lenni0451.GitTroll;

import org.bukkit.plugin.java.JavaPlugin;

public class PluginMain extends JavaPlugin {
	
	/*
	 * 
	 * To inject GitTroll into another Plugin you have to add the following code into the
	 * onEnable and onDisable of another plugin. If done correctly GitTroll should work fine and as intendet.
	 * 
	 */
	
	@Override
	public void onEnable() {
		new net.Lenni0451.GitTroll.GitTroll(this).onEnable();
	}
	
	@Override
	public void onDisable() {
		net.Lenni0451.GitTroll.GitTroll.getInstance().onDisable();
	}
	
}
