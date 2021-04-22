package com.linhnv.diary.models.requests;

import javax.validation.constraints.NotBlank;

public class TopicUpdateRq {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    public TopicUpdateRq() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
