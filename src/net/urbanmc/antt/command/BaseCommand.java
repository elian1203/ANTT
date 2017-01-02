package net.urbanmc.antt.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.urbanmc.antt.command.subs.NationSub;
import net.urbanmc.antt.command.subs.TownSub;
import net.urbanmc.antt.command.subs.TpSub;

public class BaseCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to run this command.");
			return true;
		}

		Player p = (Player) sender;

		if (args.length == 0) {
			infoMessage(p, label);
			return true;
		}
		
		
		String sub = args[0].toLowerCase();
		
		switch (sub) {
		case "nation":
			new NationSub(p);
			return true;
		case "town":
			new TownSub(p);
			return true;
		case "tp":
			new TpSub(p, args);
			return true;
		}

		return true;
	}

	private void infoMessage(Player p, String label) {
		String message = ChatColor.GOLD + "-- Allied Nation Town Teleportation --\n" 
				+ "/" + label + " tp (town)" + ChatColor.WHITE + ": Teleport to a town's spawn!\n" 
				+ ChatColor.GOLD + "/" + label + " nation" + ChatColor.WHITE + ": Toggle your nation for ANTT!\n" 
				+ ChatColor.GOLD + "/" + label + " town"+ ChatColor.WHITE + ": Toggle your town for ANTT!"
		;
		p.sendMessage(message);
	}

}
