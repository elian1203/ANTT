package net.urbanmc.antt.command.subs;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.urbanmc.antt.Functions;

public class TpSub {
	public TpSub(Player p, String[] args) {
		
		if (!p.hasPermission("antt.tp")) {
			p.sendMessage(ChatColor.RED + "You do not have permission to execute this command!");
			return;
		}
		
		if(args.length != 2) {
			p.sendMessage(ChatColor.RED + "Usage: /antt tp (town)");
			return;
		}
		
		Functions.travelTown(p, args[1]);
	}
}
