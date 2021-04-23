package com.linhnv.diary.models.requests;

import javax.validation.constraints.NotBlank;

public class FeelingRequest {
    @NotBlank
    private String name;

    public FeelingRequest() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
