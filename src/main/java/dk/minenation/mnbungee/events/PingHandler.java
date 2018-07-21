package dk.minenation.mnbungee.events;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.net.InternetDomainName;
import dk.minenation.mnbungee.MNBungee;
import dk.minenation.mnbungee.util.MNServer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Map;
import java.util.UUID;

public class PingHandler implements Listener {

    private Map<UUID, ServerInfo> serverConnections = Maps.newHashMap();

    @EventHandler
    public void onPing(ProxyPingEvent e) {
        String hostName = e.getConnection().getAddress().getHostName();
        String[] hostNameSplit = hostName.split(".");
        if (hostNameSplit.length > 1) {
            String sub = hostNameSplit[0];
            if (!sub.equals("mc")) {
                MNServer server = MNBungee.plugin.serverUtil.getServerFromName(sub);
                if (server != null) {
                    e.getResponse().setDescription(ChatColor.translateAlternateColorCodes('&', server.motd));
                }
            }
        }
    }

    @EventHandler
    public void onJoin(LoginEvent e) {
        String hostName = e.getConnection().getAddress().getHostName();
        String[] hostNameSplit = hostName.split(".");
        if (hostNameSplit.length > 1) {
            String sub = hostNameSplit[0];
            if (!sub.equals("mc")) {
                MNServer server = MNBungee.plugin.serverUtil.getServerFromName(sub);
                ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(server.id);
                if (serverInfo != null) {
                    serverConnections.put(e.getConnection().getUniqueId(), serverInfo);
                }
            }
        }
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent e) {
        ProxiedPlayer player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        if (serverConnections.containsKey(uuid)) {
            ServerInfo server = serverConnections.get(uuid);
            player.connect(server);
            serverConnections.remove(uuid);
        }
    }

}
