package com.ubb.map.menu.command.candidate;

import com.ubb.map.menu.MenuItem;
import com.ubb.map.services.CandidateCrudService;

/**
 * Created by marius on 10/13/16.
 */
abstract class BaseCandidateCommand extends MenuItem {
    CandidateCrudService candidateCrudService;

    /**
     *
     * @param key                 The key which identifies the com.ubb.map.menu.command
     * @param text                Short description for the com.ubb.map.menu.command
     * @param candidateCrudService The com.ubb.map.services wich handles candidates
     */
    BaseCandidateCommand(String key, String text, CandidateCrudService candidateCrudService) {
        super(key, text);
        this.candidateCrudService = candidateCrudService;
    }
}
