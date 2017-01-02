package net.urbanmc.antt;

import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class TownyUtil {

	public static Nation getNation(Player p) {
		try {
			return TownyUniverse.getDataSource().getResident(p.getName()).getTown().getNation();
		} catch (NotRegisteredException ex) {
			return null;
		}
	}

	public static Town getTown(String name) {
		Town town = null;

		try {
			town = TownyUniverse.getDataSource().getTown(name);
		} catch (NotRegisteredException ex) {
			;
		}

		if (town == null) {
			for (Town dataTown : TownyUniverse.getDataSource().getTowns()) {
				if (dataTown.getName().equalsIgnoreCase(name)) {
					town = dataTown;
					break;
				}
			}
		}

		return town;
	}

	public static Town getTown(Player p) {
		try {
			return TownyUniverse.getDataSource().getResident(p.getName()).getTown();
		} catch (NotRegisteredException ex) {
			return null;
		}

	}
}
