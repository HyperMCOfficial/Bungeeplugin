package dk.minenation.mnbungee.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Lobby extends Command {

    public Lobby() {
        super("Lobby", "", "hub");
    }

    public void execute(CommandSender sender, String[] args) {
        if ((sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new ComponentBuilder("Sending you to the lobby...").color(ChatColor.AQUA).create());
            ProxiedPlayer player = ((ProxiedPlayer) sender);
            player.connect(ProxyServer.getInstance().getServerInfo("lobby"));
        }
    }

}
