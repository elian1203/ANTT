package net.urbanmc.antt.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.palmergames.bukkit.towny.event.DeleteNationEvent;
import com.palmergames.bukkit.towny.event.NationRemoveTownEvent;

import net.urbanmc.antt.Functions;

public class NationListener implements Listener {

	@EventHandler
	public void onNationRemoveTown(NationRemoveTownEvent e) {
		if (Functions.isTownToggled(e.getTown().getName())) {
			Functions.setTownToggled(e.getTown().getName(), false);
		}
	}

	@EventHandler
	public void onDeleteNation(DeleteNationEvent e) {
		if (Functions.isNationToggled(e.getNationName())) {
			Functions.setNationToggled(e.getNationName(), false);
		}
	}
}
