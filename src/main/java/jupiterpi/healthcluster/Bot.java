package jupiterpi.healthcluster;

import jupiterpi.healthcluster.GlobalPreferences;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.security.auth.login.LoginException;

public class Bot {
    private JDA jda;
    private Guild guild;
    private TextChannel loggingChannel;

    private String selfName = "unknown";

    public Bot() {
        try {
            String token = GlobalPreferences.get("bot-token");
            jda = JDABuilder.createDefault(token)
                    .build();
            jda.awaitReady();

            guild = jda.getGuildById(GlobalPreferences.get("bot-guild"));
            loggingChannel = guild.getTextChannelById(GlobalPreferences.get("bot-logging-channel"));

            selfName = GlobalPreferences.get("self-name");
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void log(String message) {
        loggingChannel.sendMessage(String.format("[%s] %s", selfName, message)).queue();
    }

    public void err(String message) {
        loggingChannel.sendMessage(String.format("[%s] ERROR! %s", selfName, message)).queue();
    }
}