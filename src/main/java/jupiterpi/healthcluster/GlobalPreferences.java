package jupiterpi.healthcluster;

import jupiterpi.healthcluster.preferences.Preferences;
import jupiterpi.tools.files.Path;

public class GlobalPreferences {
    private static final Preferences preferences = new Preferences(Path.getRunningDirectory().file("preferences")); // preferences file is hidden from git; use sample-preferences as reference

    public static String get(String key) {
        return preferences.get().get(key);
    }
}