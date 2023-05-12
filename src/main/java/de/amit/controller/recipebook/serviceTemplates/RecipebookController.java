package de.amit.controller.recipebook.serviceTemplates;

import de.amit.controller.serviceTemplates.CategoryServiceImpl;

public abstract class RecipebookController<T> extends CategoryServiceImpl<T> {

    public RecipebookController(String table) {
        super("recipebook", table);
    }
}
