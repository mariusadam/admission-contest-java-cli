package com.ubb.map.menu;

/**
 * Created by marius on 10/13/16.
 */
public abstract class MenuItem implements MenuItemInterface {
    private String key;
    private String description;
    private MenuItemInterface parent;

    /**
     *
     * @param key                 The key which identifies the com.ubb.map.menu.command
     * @param description                Short description for the com.ubb.map.menu.command
     */
    public MenuItem(String key, String description) {
        this.key = key;
        this.description = description;
        this.parent = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getKey() {
        return key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public MenuItemInterface getParent() {
        return this.parent;
    }

    @Override
    public void setParent(MenuItemInterface parent) {
        this.parent = parent;
    }
}