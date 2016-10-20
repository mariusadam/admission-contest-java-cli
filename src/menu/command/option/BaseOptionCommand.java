package menu.command.option;

import controller.OptionController;
import menu.MenuItem;

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
