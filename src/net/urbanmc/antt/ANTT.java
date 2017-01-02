package net.urbanmc.antt;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import net.urbanmc.antt.command.BaseCommand;

public class ANTT extends JavaPlugin {
	
	@Override
	public void onEnable() {
		if (!getServer().getPluginManager().isPluginEnabled("Towny")) {
			getLogger().log(Level.SEVERE, "Towny plugin not found! Disabling.");
			setEnabled(false);
		}
		getCommand("antt").setExecutor(new BaseCommand());
	}
}
