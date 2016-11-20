package helper.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.yaml.snakeyaml.Yaml;

/**
 * Configuration values are stored, loaded, and saved here.
 *
 * @author Kramer Campbell
 */
public class Configuration {

    /**
     * The logger.
     */
    private static final Logger logger = Logger.getLogger(Configuration.class
            .getName());

    /**
     * The file where the configuration is stored.
     */
    private final String configFile;

    /**
     * The {@code Map} that holds the configuration values.
     */
    private Map<?, ?> config;

    public Configuration(String configFile) {
        this.configFile = configFile;
        this.load();
    }

    /**
     * Loads the configuration.
     */
    public void load() {
        Yaml yaml = new Yaml();

        try {
            config = (Map<?, ?>) yaml
                    .load(new FileReader(new File(configFile)));
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Unable to load configuration.", e);
        }
    }

    /**
     * Saves the configuration.
     */
    public void save() {
        Yaml yaml = new Yaml();

        try {
            yaml.dump(config, new PrintWriter(new File(configFile)));
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Unable to save configuration.", e);
        }
    }

    /**
     * Gets a configuration value.
     *
     * @param name
     *            The name of the configuration property.
     * @return The configuration value.
     */
    public Object get(String name) {
        return config.get((Object) name);
    }

    /**
     * Gets a configuration value.
     *
     * @param name
     *            The name of the configuration property.
     * @param defaultValue
     *            The default value to use if the configuration property does
     *            not exist.
     * @return The configuration value if the configuration property exists,
     *         otherwise the defaultValue.
     */
    public Object get(String name, Object defaultValue) {
        if (config.containsKey((Object) name)) {
            return get(name);
        } else {
            return defaultValue;
        }
    }

}