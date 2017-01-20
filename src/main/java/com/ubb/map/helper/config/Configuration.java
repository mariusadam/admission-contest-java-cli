package com.ubb.map.helper.config;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Configuration values are stored, loaded, and saved here.
 */
public class Configuration {

    /**
     * The file where the configuration is stored.
     */
    private final String configFile;

    /**
     * Yaml parser
     */
    private Yaml yaml;

    /**
     * The {@code Map} that holds the configuration values.
     */
    private Map<?, ?> config;

    public Configuration(String configFile) {
        this.configFile = configFile;
        this.yaml = new Yaml();
        this.load();
    }

    /**
     * Loads the configuration.
     */
    private void load() {
        try {
            config = (Map<?, ?>) yaml
                    .load(new FileReader(new File(configFile)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves the configuration.
     */
    public void save() {
        //TODO make the configuration available in the interface
        try {
            yaml.dump(config, new PrintWriter(new File(configFile)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets a configuration value.
     *
     * @param name The name of the configuration property.
     * @return The configuration value.
     */
    public Object get(String name) {
        return config.get((Object) name);
    }

    /**
     * Gets a configuration value.
     *
     * @param name         The name of the configuration property.
     * @param defaultValue The default value to use if the configuration property does
     *                     not exist.
     * @return The configuration value if the configuration property exists,
     * otherwise the defaultValue.
     */
    public Object get(String name, Object defaultValue) {
        if (config.containsKey((Object) name)) {
            return get(name);
        } else {
            return defaultValue;
        }
    }

    public Map<String, Map<String, String>> getDomain() {
        return (Map<String, Map<String, String>>) this.config.get("domain");
    }

    public Map<String, String> getDomainFor(String entity) {
        return this.getDomain().get(entity);
    }

    public Map<String, String> getDatabase() {
        return (Map<String, String>) this.config.get("database");
    }
}