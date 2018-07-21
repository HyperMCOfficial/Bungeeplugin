package dk.minenation.mnbungee.util;

import com.google.common.collect.Lists;
import dk.minenation.mnbungee.MNBungee;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ServerUtil {

    private List<MNServer> cachedData = Lists.newArrayList();

    public ServerUtil() {
        MNBungee.plugin.executorService.scheduleAtFixedRate(new Runnable() {

            public void run() {
                try {
                    cachedData = HTTPUtil.getAllServers();
                } catch (Exception e) {
                    //todo: log error
                }
            }

        }, 0, 10, TimeUnit.SECONDS);
    }

    public List<MNServer> getAllServers() {
        return cachedData;
    }

    public MNServer getServerFromName(String name) {
        MNServer result = null;
        for (MNServer server : getAllServers()) {
            if (server.name.trim().toLowerCase().equals(name.trim().toLowerCase())) {
                result = server;
                //return server;
            }
        }
        return result;
    }

}
