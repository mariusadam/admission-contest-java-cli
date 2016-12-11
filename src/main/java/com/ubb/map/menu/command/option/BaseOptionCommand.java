package com.ubb.map.menu.command.option;

import com.ubb.map.services.OptionCrudService;
import com.ubb.map.menu.MenuItem;

/**
 * Created by marius on 10/16/16.
 */
abstract class BaseOptionCommand extends MenuItem{
    OptionCrudService optionCrudService;

    BaseOptionCommand(String key, String description, OptionCrudService controller) {
        super(key, description);
        this.optionCrudService = controller;
    }
}
