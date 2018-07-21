package dk.minenation.mnbungee.events;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerDownHandler implements Listener {

    @EventHandler
    public void onKick(ServerKickEvent e) {
        ServerInfo kickedFrom = e.getKickedFrom();

        if (e.getPlayer().getServer().getInfo().getName().equals("lobby")) {
            BaseComponent[] reason = e.getKickReasonComponent();
            e.setCancelled(true);

            e.getPlayer().sendMessage(new ComponentBuilder("Failed to connect to the server you requested.").color(ChatColor.RED).create());
            e.getPlayer().sendMessage(reason);
        } else {
            if (kickedFrom != null) {
                if (!kickedFrom.getName().equals("lobby")) {
                    BaseComponent[] reason = e.getKickReasonComponent();
                    ServerInfo lobby = ProxyServer.getInstance().getServerInfo("lobby");

                    e.setCancelled(true);
                    e.setCancelServer(lobby);

                    e.getPlayer().sendMessage(new ComponentBuilder("You were kicked from the server you were on, so we sent you to the lobby.").color(ChatColor.RED).create());
                    e.getPlayer().sendMessage(reason);
                }
            }
        }
    }

}
