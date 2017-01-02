package net.urbanmc.antt.command.subs;

import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;

import net.md_5.bungee.api.ChatColor;
import net.urbanmc.antt.Functions;
import net.urbanmc.antt.TownyUtil;

public class NationSub {

	public NationSub(Player p, String[] args) {
		
		if(TownyUtil.getTown(p) == null) {
			sendMessage(p, "You are not in a town!");
			return;
		}
		
		Nation nation = TownyUtil.getNation(p);
		if(nation == null) {
			sendMessage(p, "Your town is not in a nation!");
			return;
		}
		
		if(!p.hasPermission("antt.nation")) {
			sendMessage(p, "You do not have permission to execute this command!");
			return;
		}
		
		if(args.length != 2) {
			sendMessage(p, "Usage: /antt nation [true/false]");
			return;
		}
		
		if(!args[1].equalsIgnoreCase("true") && !args[1].equalsIgnoreCase("false")) {
			sendMessage(p, "Usage: /antt nation [true/false]");
		}
		
				
		
		boolean option = Boolean.getBoolean(args[1]);
		Functions.setNationToggled(nation.getName(), option);
		
		p.sendMessage(String.format(ChatColor.GOLD + "You're nation has %s antt!", (option) ? "enabled" : "disabled"));
		
	}

	private void sendMessage(Player p, String s) {
		p.sendMessage(ChatColor.RED + s);
	}
	
}
