package jupiterpi.healthcluster.operator;

import jupiterpi.healthcluster.GlobalPreferences;

import java.util.ArrayList;
import java.util.List;

public class Operator {
    private List<Server> servers;

    public Operator() {
        loadServers();
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