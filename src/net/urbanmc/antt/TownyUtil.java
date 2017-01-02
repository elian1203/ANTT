package net.urbanmc.antt;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class TownyUtil {

	public static Nation getNation(String name) {
		Nation nation = null;

		try {
			nation = TownyUniverse.getDataSource().getNation(name);
		} catch (NotRegisteredException ex) {
			;
		}

		if (nation == null) {
			for (Nation dataNation : TownyUniverse.getDataSource().getNations()) {
				if (dataNation.getName().equalsIgnoreCase(name)) {
					nation = dataNation;
					break;
				}
			}
		}

		return nation;
	}

	public static Nation getNation(Player p) {
		try {
			return TownyUniverse.getDataSource().getResident(p.getName()).getTown().getNation();
		} catch (NotRegisteredException ex) {
			return null;
		}
	}

	public static Nation getNationOfTown(String townName) {
		Town town = getTown(townName);

		if (town == null)
			return null;

		try {
			return town.getNation();
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

	public static boolean townExists(String town) {
		try {
			TownyUniverse.getDataSource().getTown(town);
			return true;
		} catch (NotRegisteredException ex) {
			return false;
		}
	}

	public static boolean isTownInNation(String town) {
		try {
			TownyUniverse.getDataSource().getTown(town).getNation();
			return true;
		} catch (NotRegisteredException ex) {
			return false;
		}
	}

	public static boolean areTownAllies(String town1, String town2) {
		Nation n1 = getNationOfTown(town1), n2 = getNationOfTown(town2);

		if (n1 == n2 || (n1.hasAlly(n2) && n2.hasAlly(n1)))
			return true;
		else
			return false;
	}

	public static Location getTownSpawnLocation(Town town) {
		try {
			return town.getSpawn();
		} catch (TownyException ex) {
			return null;
		}
	}
}
