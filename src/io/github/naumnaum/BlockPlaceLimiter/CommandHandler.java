package io.github.naumnaum.BlockPlaceLimiter;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.ChatPaginator;
import org.bukkit.util.ChatPaginator.ChatPage;

public class CommandHandler implements CommandExecutor {

	private BlockPlaceLimiter plugin;

	private String chelp = "help";
	private String clist = "list";
	private String clistplayer = "lp";
	private String clistplayermax = "lpm";
	private String cversion = "version";
	private String cusedefault = "ud";
	private String cuseplayerlimits = "upl";
	private String ccanseelimits = "csl";
	private String ccanseeplaced = "csp";
	private String csetlimit = "set";
	private String cdellimit = "del";
	private String cplayermaxclear = "cpm";
	private String cplayerclear = "cp";

	private String[] help = {
			ChatColor.DARK_PURPLE + chelp + " " + ChatColor.RESET
					+ "- list commands and what they do",
			ChatColor.DARK_PURPLE + clist + " " + ChatColor.RESET
					+ "- list default block limits",
			ChatColor.DARK_PURPLE + clistplayer
					+ " - show your placed blocks if no name",
			ChatColor.DARK_PURPLE + clistplayer + " <name> " + ChatColor.RESET
					+ "- list placed blocks of this player",
			ChatColor.DARK_PURPLE + clistplayermax + " " + ChatColor.RESET
					+ "- show your max blocks",
			ChatColor.DARK_PURPLE + clistplayermax + " <name> "
					+ ChatColor.RESET + "- lsit max blocks of this player",
			ChatColor.DARK_PURPLE + cversion + " " + ChatColor.RESET
					+ "- plugin version",
			ChatColor.DARK_PURPLE + cusedefault + " " + ChatColor.RESET
					+ "- using default limits",
			ChatColor.DARK_PURPLE + cusedefault + " <true/false> "
					+ ChatColor.RESET + "- use default limits",
			ChatColor.DARK_PURPLE + cuseplayerlimits + " " + ChatColor.RESET
					+ "- using custom limits by player",
			ChatColor.DARK_PURPLE + cuseplayerlimits + " <true/false> "
					+ ChatColor.RESET + "- use custom limits by player",
			ChatColor.DARK_PURPLE + ccanseelimits + " " + ChatColor.RESET
					+ "-  players can see other players limits",
			ChatColor.DARK_PURPLE + ccanseelimits + " <true/false> "
					+ ChatColor.RESET
					+ "- set players can see other players limits",
			ChatColor.DARK_PURPLE + ccanseeplaced + " " + ChatColor.RESET
					+ "- players can see other players placed blocks",
			ChatColor.DARK_PURPLE + ccanseeplaced + " <true/false> "
					+ ChatColor.RESET
					+ "- set players can see other players placed blocks",
			ChatColor.DARK_PURPLE + csetlimit
					+ " <name> <id:meta> <max> <reason> " + ChatColor.RESET
					+ "- set player max blocks",
			ChatColor.DARK_PURPLE + cdellimit + " <name> <id:meta> "
					+ ChatColor.RESET + "- remove player max blocks",
			ChatColor.DARK_PURPLE + cplayermaxclear + " <name> "
					+ ChatColor.RESET + "- restore default max",
			ChatColor.DARK_PURPLE + cplayerclear + " <name> " + ChatColor.RESET
					+ "- reset player data" };

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Not enought arguments.");
			sender.sendMessage(ChatColor.RED
					+ "Send /bpl help to get information");
			return true;
		}
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase(chelp)) {
				return showHelp(sender);
			}
			if (args[0].equalsIgnoreCase(clist)) {
				return listBlocks(sender);
			}
			if (args[0].equalsIgnoreCase(clistplayer)) {
				return listPlayerBlocks(sender, sender.getName());
			}
			if (args[0].equalsIgnoreCase(clistplayermax)) {
				return listPlayerMaxBlocks(sender, sender.getName());
			}
			if (args[0].equalsIgnoreCase(cversion)) {
				sender.sendMessage(plugin.getDescription().getVersion());
				return true;
			}
			if (args[0].equalsIgnoreCase(cusedefault)) {
				sender.sendMessage(ChatColor.LIGHT_PURPLE + ""
						+ ConfigHandler.useDefault);
				return true;
			}
			if (args[0].equalsIgnoreCase(cuseplayerlimits)) {
				sender.sendMessage(ChatColor.LIGHT_PURPLE + ""
						+ ConfigHandler.usePlayerLimits);
				return true;
			}
			if (args[0].equalsIgnoreCase(ccanseelimits)) {
				sender.sendMessage(ChatColor.LIGHT_PURPLE + ""
						+ ConfigHandler.canSeeLimits);
				return true;
			}
			if (args[0].equalsIgnoreCase(ccanseeplaced)) {
				sender.sendMessage(ChatColor.LIGHT_PURPLE + ""
						+ ConfigHandler.canSeePlaced);
				return true;
			}
		}
		if (args.length == 2) {
			if (args[0].equalsIgnoreCase(cplayermaxclear)) {
				if (!sender.isOp()) {
					return true;
				}
				if (!isPlayer(args[1])) {
					sender.sendMessage(ChatColor.RED + args[1]
							+ " isn't a valide player");
					return true;
				}
				plugin.bplData.resetDefault(args[1]);
				sender.sendMessage(args[1]+" limits clear.");
				return true;
			}
			if (args[0].equalsIgnoreCase(cplayerclear)) {
				if (!sender.isOp()) {
					return true;
				}
				if (!isPlayer(args[1])) {
					sender.sendMessage(ChatColor.RED + args[1]
							+ " isn't a valide player");
					return true;
				}
				plugin.bplData.resetData(args[1]);
				sender.sendMessage(args[1]+" data clear.");
				return true;
			}
			if (args[0].equalsIgnoreCase(cusedefault)) {
				try {
					boolean b = Boolean.parseBoolean(args[1]);
					ConfigHandler.useDefault = b;
				} catch (Exception e) {
					e.printStackTrace();
				}
				sender.sendMessage("Use deafault set to "
						+ ChatColor.LIGHT_PURPLE + ConfigHandler.useDefault);
				return true;
			}
			if (args[0].equalsIgnoreCase(cuseplayerlimits)) {
				try {
					boolean b = Boolean.parseBoolean(args[1]);
					ConfigHandler.usePlayerLimits = b;
				} catch (Exception e) {
					e.printStackTrace();
				}
				sender.sendMessage("Use custom limits set to "
						+ ChatColor.LIGHT_PURPLE
						+ ConfigHandler.usePlayerLimits);
				return true;
			}
			if (args[0].equalsIgnoreCase(ccanseelimits)) {
				try {
					boolean b = Boolean.parseBoolean(args[1]);
					ConfigHandler.canSeeLimits = b;
				} catch (Exception e) {
					e.printStackTrace();
				}
				sender.sendMessage("Can see limits set to "
						+ ChatColor.LIGHT_PURPLE + ConfigHandler.canSeeLimits);
				return true;
			}
			if (args[0].equalsIgnoreCase(ccanseeplaced)) {
				try {
					boolean b = Boolean.parseBoolean(args[1]);
					ConfigHandler.canSeePlaced = b;
				} catch (Exception e) {
					e.printStackTrace();
				}
				sender.sendMessage("Can see placed set to "
						+ ChatColor.LIGHT_PURPLE + ConfigHandler.canSeePlaced);
				return true;
			}
			if (args[0].equalsIgnoreCase(chelp)) {
				if (!Util.isNum(args[1])) {
					sender.sendMessage(ChatColor.RED + "Use /bpl help #");
					sender.sendMessage(ChatColor.RED + "Where # is a number");
					return true;
				} else {
					int page = 0;
					try {
						page = Integer.parseInt(args[1]);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return showHelp(sender, page);
				}
			}
			if (args[0].equalsIgnoreCase(clist)) {
				if (!Util.isNum(args[1])) {
					sender.sendMessage(ChatColor.RED + "Use /bpl list #");
					sender.sendMessage(ChatColor.RED + "Where # is a number");
					return true;
				} else {
					int page = 0;
					try {
						page = Integer.parseInt(args[1]);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return listBlocks(sender, page);
				}
			}
			if (args[0].equalsIgnoreCase(clistplayer)) {
				if (ConfigHandler.canSeePlaced && !sender.isOp()) {
					sender.sendMessage("Only OPs can list other players");
					return true;
				}
				if (Util.isNum(args[1])) {
					int page = 0;
					try {
						page = Integer.parseInt(args[1]);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return listPlayerBlocks(sender, sender.getName(), page);
				} else if (!isPlayer(args[1])) {
					sender.sendMessage(ChatColor.RED + args[1]
							+ " isn't a valide player");
					return true;
				} else {
					return listPlayerBlocks(sender, args[1]);
				}
			}
			if (args[0].equalsIgnoreCase(clistplayermax)) {
				if (ConfigHandler.canSeeLimits && !sender.isOp()) {
					sender.sendMessage("Only OPs can list other players");
					return true;
				}
				if (Util.isNum(args[1])) {
					int page = 0;
					try {
						page = Integer.parseInt(args[1]);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return listPlayerMaxBlocks(sender, sender.getName(), page);
				} else if (!isPlayer(args[1])) {
					sender.sendMessage(ChatColor.RED + args[1]
							+ " isn't a valide player");
					return true;
				} else {
					return listPlayerMaxBlocks(sender, args[1]);
				}
			}
		}
		if (args.length == 3) {
			if (args[0].equalsIgnoreCase(clistplayer)) {
				if (ConfigHandler.canSeePlaced && !sender.isOp()) {
					sender.sendMessage("Only OPs can list other players");
					return true;
				}
				if (!isPlayer(args[1])) {
					sender.sendMessage(ChatColor.RED + args[1]
							+ " isn't a valide player");
				} else {
					if (!Util.isNum(args[2])) {
						sender.sendMessage(ChatColor.RED
								+ "Use /bpl listplayer <name> #");
						sender.sendMessage(ChatColor.RED
								+ "Where # is a number");
						return true;
					} else {
						int page = 0;
						try {
							page = Integer.parseInt(args[2]);
						} catch (Exception e) {
							e.printStackTrace();
						}
						return listPlayerBlocks(sender, args[1], page);
					}

				}
			}
			if (args[0].equalsIgnoreCase(clistplayermax)) {
				if (ConfigHandler.canSeeLimits && !sender.isOp()) {
					sender.sendMessage("Only OPs can list other players");
					return true;
				}
				if (!isPlayer(args[1])) {
					sender.sendMessage(ChatColor.RED + args[1]
							+ " isn't a valide player");
				} else {
					if (!Util.isNum(args[2])) {
						sender.sendMessage(ChatColor.RED
								+ "Use /bpl listplayer <name> #");
						sender.sendMessage(ChatColor.RED
								+ "Where # is a number");
						return true;
					} else {
						int page = 0;
						try {
							page = Integer.parseInt(args[2]);
						} catch (Exception e) {
							e.printStackTrace();
						}
						return listPlayerMaxBlocks(sender, args[1], page);
					}

				}
			}
			if (args[0].equalsIgnoreCase(cdellimit)) {
				if (!sender.isOp()) {
					return true;
				}
				if (!isPlayer(args[1])) {
					sender.sendMessage(ChatColor.RED + args[1]
							+ " isn't a valide player");
					return true;
				}
				BPLBlock block = null;
				try {
					int id = Integer.parseInt(args[2].split(":")[0]);
					byte meta = Byte.parseByte(args[2].split(":")[1]);
					block = new BPLBlock(id, meta, args[4]);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (block != null) {
						plugin.bplData.setMax(args[1], block,
								Integer.parseInt(args[3]));
						sender.sendMessage("Succes removing block: " + block);
						return true;
					}
				}
			}
		}
		if (args.length == 5) {
			if (args[0].equalsIgnoreCase(csetlimit)) {
				if (!sender.isOp()) {
					return true;
				}
				if (!isPlayer(args[1])) {
					sender.sendMessage(ChatColor.RED + args[1]
							+ " isn't a valide player");
					return true;
				}
				if (args[3].charAt(0) == '-') {
					if (!Util.isNum((args[3].split("-")[1]))) {
						sender.sendMessage(ChatColor.RED
								+ "Use /bpl setlimit <name> <id:meta> # <reason>");
						sender.sendMessage(ChatColor.RED
								+ "Where # is a number");
						return true;
					}
				} else {
					if (!Util.isNum(args[3])) {
						sender.sendMessage(ChatColor.RED
								+ "Use /bpl setlimit <name> <id:meta> # <reason>");
						sender.sendMessage(ChatColor.RED
								+ "Where # is a number");
						return true;
					}
				}
				BPLBlock block = null;
				try {
					int id = Integer.parseInt(args[2].split(":")[0]);
					byte meta = Byte.parseByte(args[2].split(":")[1]);
					block = new BPLBlock(id, meta, args[4]);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (block != null) {
						plugin.bplData.setMax(args[1], block,
								Integer.parseInt(args[3]));
						sender.sendMessage("Succes adding block: " + block);
						return true;
					}
				}
			}
		}
		sender.sendMessage("Something is wrong.");
		sender.sendMessage("Try using /bpl help");
		return true;
	}

	private boolean showHelp(CommandSender sender, int page) {
		ChatPage pagina = ChatPaginator.paginate(Util.toLine(help), page, 100,
				9);
		String head = ChatColor.GOLD + "Block Place Limiter Help - Page: "
				+ ChatColor.DARK_PURPLE + page + " of "
				+ pagina.getTotalPages() + ChatColor.RESET;
		sender.sendMessage(head);
		sendPage(sender, pagina);
		return true;
	}

	private boolean showHelp(CommandSender sender) {
		return showHelp(sender, 1);
	}

	private boolean listPlayerMaxBlocks(CommandSender sender, String name,
			int page) {
		String[] list = plugin.bplData.listPlayerMaxBlocks(name);
		String s = Util.toLine(list);
		ChatPage pagina = ChatPaginator.paginate(s, page, 100, 9);
		String head = ChatColor.GOLD + "Limited Blocks - Page "
				+ ChatColor.DARK_PURPLE + page + " of "
				+ pagina.getTotalPages() + ChatColor.RESET;
		sender.sendMessage(head);
		sendPage(sender, pagina);
		return true;
	}

	private boolean listPlayerBlocks(CommandSender sender, String name, int page) {
		String[] list = plugin.bplData.listPlayerBlocks(name);
		String s = Util.toLine(list);
		ChatPage pagina = ChatPaginator.paginate(s, page, 100, 9);
		String head = ChatColor.GOLD + "Limited Blocks - Page "
				+ ChatColor.DARK_PURPLE + page + " of "
				+ pagina.getTotalPages() + ChatColor.RESET;
		sender.sendMessage(head);
		sendPage(sender, pagina);
		return true;
	}

	private boolean listBlocks(CommandSender sender, int page) {
		String[] list = plugin.bplData.listDefaultBlocks();
		String s = Util.toLine(list);
		ChatPage pagina = ChatPaginator.paginate(s, page, 100, 9);
		String head = ChatColor.GOLD + "Limited Blocks page "
				+ ChatColor.DARK_PURPLE + page + ChatColor.RESET;
		sender.sendMessage(head);
		sendPage(sender, pagina);
		return true;
	}

	public CommandHandler(BlockPlaceLimiter plugin) {
		this.plugin = plugin;
	}

	private boolean listBlocks(CommandSender sender) {
		return listBlocks(sender, 1);
	}

	private boolean listPlayerBlocks(CommandSender sender, String name) {
		return listPlayerBlocks(sender, name, 1);
	}

	private boolean listPlayerMaxBlocks(CommandSender sender, String name) {
		return listPlayerMaxBlocks(sender, name, 1);
	}

	private boolean isPlayer(String name) {
		return (plugin.getServer().getPlayer(name) != null);
	}

	private void sendPage(CommandSender to, ChatPage page) {
		for (String line : page.getLines())
			to.sendMessage(line);
	}
}
