package jupiterpi.healthcluster.operator;

import jupiterpi.healthcluster.GlobalPreferences;
import jupiterpi.healthcluster.bot.Bot;
import jupiterpi.healthcluster.http.Http;

import java.util.ArrayList;
import java.util.List;

public class Operator {
    private Bot bot;

    private List<Server> servers;
    private Server leader;

    public Operator(Bot bot) {
        this.bot = bot;

        loadServers();

        try {
            List<String> suggestedLeaderUrls = new ArrayList<>();
            for (Server server : servers) {
                Http.async("get", server.getKnockUrl(), (code, response) -> {
                    if (code == 200) {
                        suggestedLeaderUrls.add(response);
                    }
                });
            }
            Thread.sleep(Integer.parseInt(GlobalPreferences.get("knock-timeout")) * 1000);

            if (suggestedLeaderUrls.size() == 0) {
                bot.err("No suggested urls after knocking. ");
            }
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
                bot.err("No suggested leader url!");
                new Exception("No suggested leader url!").printStackTrace();
            }
            for (Server server : servers) {
                if (server.getUrl().equals(suggestedLeaderUrl)) {
                    leader = server;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void loadServers() {
        servers = new ArrayList<>();
        for (int i = 1; ; i++) {
            String str = GlobalPreferences.get("server." + i);
            if (str == null) {
                break;
            }
            servers.add(new Server(str));
        }
    }
}