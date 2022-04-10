package configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationClient {
    private static final String PATH = "src/server/resources/settings.txt";

    private String host;
    private int port;

    public ConfigurationClient() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(PATH));
            port = Integer.parseInt(properties.getProperty("PORT", "65500"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }
}
