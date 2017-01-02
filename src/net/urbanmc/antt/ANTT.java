package net.urbanmc.antt;

import java.util.ArrayList;

import org.bukkit.plugin.java.JavaPlugin;

public class ANTT extends JavaPlugin {

	private static ArrayList<String> townsToggled = new ArrayList<String>();
	
	@Override
	public void onEnable() {
		
	}
	
	public static ArrayList<String> getTownsToggled() {
		return townsToggled;
	}
}
