package net.urbanmc.antt.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.palmergames.bukkit.towny.event.DeleteNationEvent;
import com.palmergames.bukkit.towny.event.NationRemoveTownEvent;

import net.urbanmc.antt.Functions;

public class NationListener implements Listener {

	@EventHandler
	public void onNationRemoveTown(NationRemoveTownEvent e) {
		Functions.setTownToggled(e.getTown().getName(), false);
	}

	@EventHandler
	public void onDeleteNation(DeleteNationEvent e) {
		Functions.setNationToggled(e.getNationName(), false);
	}
}
