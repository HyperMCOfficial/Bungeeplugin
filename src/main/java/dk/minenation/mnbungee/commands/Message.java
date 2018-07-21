package dk.minenation.mnbungee.commands;

import com.google.common.collect.Maps;
import dk.minenation.mnbungee.MNBungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Map;

public class Message extends Command {

    public Message(MNBungee plugin) {
        super("Message", "", "msg", "pm", "tell", "whisper");
        ProxyServer.getInstance().getPluginManager().registerCommand(plugin, new Reply());
    }

    public void execute(CommandSender sender, String[] args) {
        if ((sender instanceof ProxiedPlayer)) {
            if (args.length >= 2) {
                String receiverName = args[0];
                ProxiedPlayer receiver = ProxyServer.getInstance().getPlayer(receiverName);
                if (receiver != null) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        sb.append(args[i]).append(" ");
                    }
                    String msg = sb.toString();
                    receiver.sendMessage(new ComponentBuilder("[").color(ChatColor.DARK_GRAY).append(sender.getName() + " -> You").color(ChatColor.AQUA).append("]").color(ChatColor.DARK_GRAY).append(" " + msg).color(ChatColor.AQUA).create());
                    sender.sendMessage(new ComponentBuilder("[").color(ChatColor.DARK_GRAY).append("You -> " + receiver.getName()).color(ChatColor.AQUA).append("]").color(ChatColor.DARK_GRAY).append(" " + msg).color(ChatColor.AQUA).create());
                } else {
                    sender.sendMessage(new ComponentBuilder("The player '" + receiverName + "' is offline.").color(ChatColor.RED).create());
                }
            } else {
                sender.sendMessage(new ComponentBuilder("Invalid syntax. /msg <player> <message>").color(ChatColor.RED).create());
            }
        }
    }
}

class Reply extends Command {

    Map<ProxiedPlayer, ProxiedPlayer> replyMap = Maps.newHashMap();

    public Reply() {
        super("Reply", "", "r");
    }

    public void setReply(ProxiedPlayer sender, ProxiedPlayer receiver) {
        replyMap.put(sender, receiver);
    }

    public void execute(CommandSender sender, String[] args) {
        if ((sender instanceof ProxiedPlayer)) {
            if (args.length >= 1) {
                if (replyMap.containsKey(sender)) {
                    ProxiedPlayer receiver = replyMap.get(sender);
                    if (receiver.isConnected()) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < args.length; i++) {
                            sb.append(args[i]).append(" ");
                        }
                        String msg = sb.toString();
                        receiver.sendMessage(new ComponentBuilder("[").color(ChatColor.DARK_GRAY).append(sender.getName() + " -> You").color(ChatColor.AQUA).append("]").color(ChatColor.DARK_GRAY).append(" " + msg).color(ChatColor.AQUA).create());
                        sender.sendMessage(new ComponentBuilder("[").color(ChatColor.DARK_GRAY).append("You -> " + receiver.getName()).color(ChatColor.AQUA).append("]").color(ChatColor.DARK_GRAY).append(" " + msg).color(ChatColor.AQUA).create());
                    } else {
                        sender.sendMessage(new ComponentBuilder("You have nobody to reply to.").color(ChatColor.RED).create());
                        replyMap.remove(sender);
                    }
                } else {
                    sender.sendMessage(new ComponentBuilder("You have nobody to reply to.").color(ChatColor.RED).create());
                }
            } else {
                sender.sendMessage(new ComponentBuilder("Invalid syntax. /reply <message>").color(ChatColor.RED).create());
            }
        }
    }
}