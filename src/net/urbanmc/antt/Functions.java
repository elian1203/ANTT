package net.urbanmc.antt;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;

import net.urbanmc.antt.gson.ToggledNation;

public class Functions {

	public static void travelTown(Player p, String townName) {
		Town town = TownyUtil.getTown(p);

		if (town == null) {
			p.sendMessage(ChatColor.RED + "You are not in a town.");
			return;
		}

		if (!TownyUtil.isTownInNation(town.getName())) {
			p.sendMessage(ChatColor.RED + "Your town is not in a nation.");
			return;
		}

		if (!TownyUtil.townExists(townName)) {
			p.sendMessage(ChatColor.RED + "That town does not exist.");
			return;
		}

		if (!TownyUtil.isTownInNation(townName)) {
			p.sendMessage(ChatColor.RED + "That town is not in a nation.");
			return;
		}

		if (!TownyUtil.areTownAllies(town.getName(), townName)) {
			p.sendMessage(ChatColor.RED + "Your nation is not allied with that town's nation");
			return;
		}

		Town travelTown = TownyUtil.getTown(townName);
		Nation travelNation = TownyUtil.getNationOfTown(travelTown.getName());

		if (!isNationToggled(travelNation.getName())) {
			p.sendMessage(ChatColor.RED + "That nation has not enabled ANTT.");
			return;
		}

		if (!isTownToggled(travelTown.getName())) {
			p.sendMessage(ChatColor.RED + "That town has not enabled ANTT.");
			return;
		}

		Location spawn = TownyUtil.getTownSpawnLocation(travelTown);

		if (spawn == null) {
			p.sendMessage(ChatColor.RED + "That town does not has a set spawn.");
			return;
		}

		p.teleport(spawn);
		p.sendMessage(ChatColor.GREEN + "You have teleported to " + travelTown.getName() + ".");
	}

	public static boolean isNationToggled(String nation) {
		for (ToggledNation toggledNation : Storage.getInstance().getToggled()) {
			if (nation.equals(toggledNation.getNation()))
				return true;
		}

		return false;
	}

	public static boolean isTownToggled(String town) {
		return getToggledTowns().contains(town);
	}

	public static void setNationToggled(String nation, boolean toggled) {
		if (toggled == isNationToggled(nation))
			return;

		if (toggled) {
			ToggledNation toggledNation = new ToggledNation(nation);

			Storage.getInstance().getToggled().add(toggledNation);
		} else {
			ToggledNation toggledNation = getToggledNation(nation);

			Storage.getInstance().getToggled().remove(toggledNation);
		}

		Storage.getInstance().saveGson();
	}

	public static void setTownToggled(String town, boolean toggled) {
		if (toggled == isTownToggled(town))
			return;

		ToggledNation toggledNation = getToggledNationOfTown(town);

		if (toggledNation == null)
			return;

		if (toggled) {
			toggledNation.addToggledTown(town);
		} else {
			toggledNation.removeToggledTown(town);
		}

		Storage.getInstance().saveGson();
	}

	public static List<String> getToggledTowns() {
		List<String> toggledTowns = new ArrayList<String>();

		for (ToggledNation nation : Storage.getInstance().getToggled()) {
			toggledTowns.addAll(nation.getToggledTowns());
		}

		return toggledTowns;
	}

	public static ToggledNation getToggledNation(String nation) {
		for (ToggledNation toggledNation : Storage.getInstance().getToggled()) {
			if (nation.equals(toggledNation.getNation()))
				return toggledNation;
		}

		return null;
	}

	public static ToggledNation getToggledNationOfTown(String town) {
		Nation nation = TownyUtil.getNationOfTown(town);

		if (nation == null)
			return null;

		String name = nation.getName();

		if (!isNationToggled(name))
			return null;

		return getToggledNation(name);
	}
}
