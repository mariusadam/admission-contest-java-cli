package com.ubb.map.controller;

/**
 * Created by marius on 1/21/2017.
 */
public enum TabType {
    DEPARTMENTS("Departmants"),
    CANDIDATES("Candidates"),
    OPTIONS("Options"),
    USERS("Users"),
    ROLES("Roles");

    private String tabName;

    TabType(String tabName) {
        this.tabName = tabName;
    }


    @Override
    public String toString() {
        return tabName;
    }
}
