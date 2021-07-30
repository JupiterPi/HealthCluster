package jupiterpi.healthcluster.leader;

import jupiterpi.healthcluster.Server;

import java.util.ArrayList;
import java.util.List;

public class Leader {
    private List<Server> availableClients;
    private List<Server> activeClients;

    public Leader(List<Server> servers) {
        availableClients = new ArrayList<>(servers);
        activeClients = new ArrayList<>();
    }

    public boolean registerClient(String clientUrl) {
        for (Server server : availableClients) {
            if (server.getUrl().equals(clientUrl)) {
                activeClients.add(server);
                return true;
            }
        }
        return false;
    }
}