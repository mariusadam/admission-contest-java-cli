package com.ubb.map.menu.command.option;

import com.ubb.map.controller.OptionController;
import com.ubb.map.menu.MenuItem;

/**
 * Created by marius on 10/16/16.
 */
abstract class BaseOptionCommand extends MenuItem{
    OptionController optionController;

    BaseOptionCommand(String key, String description, OptionController controller) {
        super(key, description);
        this.optionController = controller;
    }
}
