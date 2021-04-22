package com.linhnv.diary.models.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TopicChangeDefault {

    @NotBlank
    private String id;

    @NotNull
    private boolean isDefault;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
