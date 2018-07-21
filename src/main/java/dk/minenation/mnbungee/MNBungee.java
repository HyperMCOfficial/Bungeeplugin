package dk.minenation.mnbungee;

import dk.minenation.mnbungee.commands.Lobby;
import dk.minenation.mnbungee.commands.Message;
import dk.minenation.mnbungee.events.PingHandler;
import dk.minenation.mnbungee.events.ServerDownHandler;
import dk.minenation.mnbungee.util.ServerUtil;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class MNBungee extends Plugin {

    public ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    public static MNBungee plugin;
    public ServerUtil serverUtil;

    @Override
    public void onEnable() {
        plugin = this;
        serverUtil = new ServerUtil();

        getLogger().info("The MineNation Bungee System is active.");

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Message(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Lobby());

        //ProxyServer.getInstance().getPluginManager().registerListener(this, new PingHandler());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new ServerDownHandler());
    }

}
