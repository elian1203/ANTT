package net.urbanmc.antt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;



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

		Bukkit.broadcastMessage(townname);
		Town travelTown;
		String travelTownName = null;
		if (!isKing) {
			try {
			travelTownName = matchTown(townname);
			} 
			catch (NullPointerException e) {
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
			removeTown(travelTownName);
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
	
	private static String matchTown(String name) {
		if (ANTT.getTownsToggled().isEmpty())
			return null;
		for (String string : ANTT.getTownsToggled()) {
			if (name.equalsIgnoreCase(string))
				return string;
		}
		return null;
	}

	private static void removeTown(String townname) {
		if (ANTT.getTownsToggled().contains(townname))
			ANTT.getTownsToggled().remove(townname);
		return;
	}
}
