package de.amit.model.dogschool;

import java.util.UUID;

public class Video {

    private UUID uuid;
    private String name;
    private UUID sectionID;

    public String getName() {
        return name;
    }

    public UUID getSectionID() {
        return sectionID;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSectionID(UUID sectionID) {
        this.sectionID = sectionID;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

}
