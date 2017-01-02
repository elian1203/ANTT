package net.urbanmc.antt.command.subs;

import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.object.Town;

import net.md_5.bungee.api.ChatColor;
import net.urbanmc.antt.Functions;
import net.urbanmc.antt.TownyUtil;

public class TownSub {
	public TownSub(Player p, String[] args) {
		Town town = TownyUtil.getTown(p);

		if (town == null) {
			sendMessage(p, "You are not in a town.");
			return;
		}

		if (!p.hasPermission("antt.town")) {
			sendMessage(p, "You do not have permission to toggle this.");
			return;
		}

		if (TownyUtil.getNation(p) == null) {
			sendMessage(p, "Your town is not in a nation.");
			return;
		}

		if (args.length != 2) {
			sendMessage(p, "Usage: /antt town [true/false]");
			return;
		}

		if (!args[1].equalsIgnoreCase("true") && !args[1].equalsIgnoreCase("false")) {
			sendMessage(p, "Usage: /antt town [true/false]");
		}

		if (!Functions.isNationToggled(TownyUtil.getNation(p).getName())) {
			p.sendMessage(ChatColor.RED + "You cannot toggle your town for ANTT becauase your nation has it disabled.");
			return;
		}

		boolean option = Boolean.getBoolean(args[1]);
		Functions.setTownToggled(town.getName(), option);

		p.sendMessage(String.format(ChatColor.GOLD + "You have %s ANTT for your nation.", (option) ? "enabled" : "disabled"));

	}

	private void sendMessage(Player p, String s) {
		p.sendMessage(ChatColor.RED + s);
	}
}
