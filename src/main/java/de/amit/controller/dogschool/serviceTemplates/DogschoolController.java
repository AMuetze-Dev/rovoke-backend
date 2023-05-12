package de.amit.controller.dogschool.serviceTemplates;

import de.amit.controller.serviceTemplates.CategoryServiceImpl;

public abstract class DogschoolController<T> extends CategoryServiceImpl<T> {

    public DogschoolController(String table) {
        super("dogschool", table);
    }
}
