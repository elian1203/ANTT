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
			new NationSub(p, args);
			return true;
		case "town":
			new TownSub(p, args);
			return true;
		case "tp":
			new TpSub(p, args);
			return true;
		}

		infoMessage(p, label);

		return true;
	}

	private void infoMessage(Player p, String label) {
		StringBuilder message = new StringBuilder();

		message.append(ChatColor.GOLD + "-- Allied Nation Town Teleportation --\n");
		message.append("/" + label + " tp (town)" + ChatColor.WHITE + ": Teleport to a town's spawn!\n");
		message.append(ChatColor.GOLD + "/" + label + " town" + ChatColor.WHITE + ": Toggle whether or not your town has ANTT enabled.\n");
		message.append(ChatColor.GOLD + "/" + label + " nation" + ChatColor.WHITE + ": Toggle whether or not your nation has ANTT enabled.");

		p.sendMessage(message.toString());
	}

}
