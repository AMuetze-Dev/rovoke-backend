package de.amit.model.dogschool;

import java.util.UUID;

public class Course {

    private UUID uuid;
    private String name;

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

}
