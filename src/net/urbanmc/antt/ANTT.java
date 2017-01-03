package net.urbanmc.antt;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import net.urbanmc.antt.command.BaseCommand;
import net.urbanmc.antt.listener.NationListener;

public class ANTT extends JavaPlugin {

	@Override
	public void onEnable() {
		if (!getServer().getPluginManager().isPluginEnabled("Towny")) {
			getLogger().log(Level.SEVERE, "Towny plugin not found! Disabling.");
			setEnabled(false);
		}

		getServer().getPluginManager().registerEvents(new NationListener(), this);
		getCommand("antt").setExecutor(new BaseCommand());
	}
}
