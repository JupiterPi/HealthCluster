package jupiterpi.healthcluster.preferences;

import jupiterpi.tools.files.Path;
import jupiterpi.tools.files.TextFile;

import java.util.HashMap;
import java.util.Map;

public class Preferences {
    private TextFile file;

    public Preferences(Path file) {
        this.file = new TextFile(file);
    }

    public Map<String, String> get() {
        Map<String, String> values = new HashMap<>();
        for (String line : file.getFile()) {
            String[] parts = line.split(": ");
            values.put(parts[0], parts[1]);
        }
        return values;
    }
}