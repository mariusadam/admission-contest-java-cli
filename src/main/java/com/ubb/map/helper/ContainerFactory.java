package com.ubb.map.helper;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by marius on 08.12.2016.
 */
public class ContainerFactory {
    private Map<String, ServiceContainer> serviceContainerMap;

    public final static String DEFAULT_CONFIG_PATH = "src/main/resources/config/config.yml";

    private static ContainerFactory ourInstance = new ContainerFactory();

    public static ContainerFactory getInstance() {
        return ourInstance;
    }

    private ContainerFactory() {
        this.serviceContainerMap = new Hashtable<>();
    }

    public ServiceContainer getServiceContainer() {
        return this.getServiceContainer(DEFAULT_CONFIG_PATH);
    }

    public ServiceContainer getServiceContainer(String configPath){
        if (!this.serviceContainerMap.containsKey(configPath)) {
            this.serviceContainerMap.put(configPath, new ServiceContainer(configPath));
        }

        return this.serviceContainerMap.get(configPath);
    }
}
