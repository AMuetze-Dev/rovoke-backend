package de.amit.model.dogschool;

import java.util.UUID;

public class Section {

    private UUID uuid;
    private String name;
    private UUID courseID;

    public UUID getCourseID() {
        return courseID;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setCourseID(UUID courseID) {
        this.courseID = courseID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
