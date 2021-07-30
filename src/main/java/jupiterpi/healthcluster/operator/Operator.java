package jupiterpi.healthcluster.operator;

import jupiterpi.healthcluster.GlobalPreferences;
import jupiterpi.healthcluster.Bot;
import jupiterpi.healthcluster.Http;

import java.util.ArrayList;
import java.util.List;

public class Operator {
    private Bot bot;

    private List<Server> servers;
    private Server leader;

    public Operator(Bot bot) {
        this.bot = bot;

        servers = new ArrayList<>();
        for (int i = 0; ; i++) {
            String id = i == 0 ? ""+i : "self";
            String str = GlobalPreferences.get("server." + id);
            if (str == null) {
                break;
            }
            servers.add(new Server(str));
        }
        List<Server> otherServers = servers.subList(1, servers.size());

        try {
            List<String> suggestedLeaderUrls = new ArrayList<>();
            for (Server server : otherServers) {
                Http.async("get", server.getKnockUrl(), (code, response) -> {
                    if (code == 200) {
                        suggestedLeaderUrls.add(response);
                    }
                });
            }
            Thread.sleep(Integer.parseInt(GlobalPreferences.get("knock-timeout")) * 1000);

            String suggestedLeaderUrl = null;
            for (String url : suggestedLeaderUrls) {
                if (suggestedLeaderUrl == null) {
                    suggestedLeaderUrl = url;
                } else {
                    if (!url.equals(suggestedLeaderUrl)) {
                        bot.err("Suggested leader urls mismatch! " + suggestedLeaderUrl + " and " + url);
                        new Exception("Suggested leader urls mismatch!").printStackTrace();
                    }
                }
            }

            if (suggestedLeaderUrl == null) {
                bot.log("Electing self as leader. ");
                leader = servers.get(0);
            }
            for (Server server : servers) {
                if (server.getUrl().equals(suggestedLeaderUrl)) {
                    bot.log(String.format("Acknowledging %s as leader. ", server.toString()));
                    leader = server;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}