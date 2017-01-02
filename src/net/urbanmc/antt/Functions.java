package net.urbanmc.antt;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;

import net.urbanmc.antt.gson.ToggledNation;

public class Functions {

	public static void travelTown(Player p, String townname) {
		if (!p.hasPermission("antt.travel") && !p.hasPermission("antt.kingtravel")) {
			p.sendMessage(ChatColor.RED + "You do not have permission to do this!");
			return;
		}

		boolean isKing = (p.hasPermission("antt.kingtravel")) ? true : false;

		Nation nation = TownyUtil.getNation(p);

		if (nation == null) {
			p.sendMessage(ChatColor.RED + "You are not in a nation!");
			return;
		}

		Town travelTown;
		String travelTownName = null;
		if (!isKing) {
			try {
				travelTownName = matchTown(townname);
			} catch (NullPointerException e) {
				p.sendMessage(ChatColor.RED + "You are not allowed to travel to that town!");
				return;
			}

			if (travelTownName == null) {
				p.sendMessage(ChatColor.RED + "You are not allowed to travel to that town!");
				return;
			}

			travelTown = TownyUtil.getTown(travelTownName);
		} else {
			travelTown = TownyUtil.getTown(townname);

			if (travelTown == null) {
				p.sendMessage(ChatColor.RED + "Town doesn't exist!");
				return;
			}
		}

		Nation travelNation;
		try {
			travelNation = travelTown.getNation();
		} catch (NotRegisteredException e) {
			p.sendMessage(ChatColor.RED + "The town you want to travel to is not in a nation!");
			return;
		}

		boolean inNation = false;
		boolean inAlliedNation = false;

		if (travelNation.getName().equalsIgnoreCase(nation.getName()))
			inNation = true;
		if (travelNation.getAllies().contains(nation))
			inAlliedNation = true;

		if (!inNation && !inAlliedNation) {
			p.sendMessage(ChatColor.RED + "You are not allowed to travel to that town!");
			return;
		}

		Location spawnLoc = null;
		try {
			spawnLoc = travelTown.getSpawn();
		} catch (TownyException e) {
			p.sendMessage("Error teleporting to town! Please contact an administrator!");
			e.printStackTrace();
			return;
		}

		p.teleport(spawnLoc);
		p.sendMessage(ChatColor.GREEN + "You have teleported to " + travelTown.getName() + ChatColor.GREEN + "!");
		return;

	}

	public static boolean isNationToggled(String nation) {
		for (ToggledNation toggledNation : Storage.getInstance().getToggled()) {
			if (nation.equals(toggledNation.getNation()))
				return true;
		}

		return false;
	}

	public static boolean isTownToggled(String town) {
		ToggledNation toggledNation = getToggledNationOfTown(town);

		if (toggledNation == null)
			return false;

		for (String toggledTown : toggledNation.getToggledTowns()) {
			if (toggledTown.equals(town))
				return true;
		}

		return false;
	}

	public void setNationToggled(String nation, boolean toggled) {
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

	public void setTownToggled(String town, boolean toggled) {
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

	private static String matchTown(String name) {
		for (String string : getToggledTowns()) {
			if (name.equalsIgnoreCase(string))
				return string;
		}

		return null;
	}

	private static List<String> getToggledTowns() {
		List<String> toggledTowns = new ArrayList<String>();

		for (ToggledNation nation : Storage.getInstance().getToggled()) {
			toggledTowns.addAll(nation.getToggledTowns());
		}

		return toggledTowns;
	}

	private static ToggledNation getToggledNation(String nation) {
		for (ToggledNation toggledNation : Storage.getInstance().getToggled()) {
			if (nation.equals(toggledNation.getNation()))
				return toggledNation;
		}

		return null;
	}

	private static ToggledNation getToggledNationOfTown(String town) {
		Nation nation = TownyUtil.getNationOfTown(town);

		if (nation == null)
			return null;

		String name = nation.getName();

		if (!isNationToggled(name))
			return null;

		return getToggledNation(name);
	}
}
