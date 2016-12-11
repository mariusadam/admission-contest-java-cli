package com.ubb.map.menu.command.candidate;

import com.ubb.map.menu.MenuItem;
import com.ubb.map.controller.CandidateController;

/**
 * Created by marius on 10/13/16.
 */
abstract class BaseCandidateCommand extends MenuItem {
    CandidateController candidateController;

    /**
     *
     * @param key                 The key which identifies the com.ubb.map.menu.command
     * @param text                Short description for the com.ubb.map.menu.command
     * @param candidateController The com.ubb.map.controller wich handles candidates
     */
    BaseCandidateCommand(String key, String text, CandidateController candidateController) {
        super(key, text);
        this.candidateController = candidateController;
    }
}
