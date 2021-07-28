package jupiterpi.healthcluster.operator;

public class Server {
    private String name;
    private String url;

    public Server(String str) {
        String[] parts = str.split("\"");
        name = parts[1];
        url = parts[2].substring(1);
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url.substring(0, url.length()- (url.endsWith("/") ? 1 : 0) );
    }

    @Override
    public String toString() {
        return "Server{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}