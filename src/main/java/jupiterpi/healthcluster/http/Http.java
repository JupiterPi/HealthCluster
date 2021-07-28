package jupiterpi.healthcluster.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.BiConsumer;

public class Http {
    public static String sync(String method, String url) {
        try {
            URL urlObj = new URL(url);

            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
            con.setRequestMethod(method.toUpperCase());

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void async(String method, String url, BiConsumer<Integer, String> callback) {
        new Thread(() -> {
            try {
                URL urlObj = new URL(url);

                HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
                con.setRequestMethod(method.toUpperCase());

                int code = con.getResponseCode();

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}