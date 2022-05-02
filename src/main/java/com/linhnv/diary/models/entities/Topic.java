package com.linhnv.diary.models.entities;

import com.linhnv.diary.models.bos.StatusEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "topics")
public class Topic extends AbsEntity{
    @Column(columnDefinition = "VARCHAR(100)")
    private String name;

    @Column(columnDefinition = "VARCHAR(500)")
    private String description;

    @Column(name = "status", columnDefinition = "VARCHAR(20) default 'ACTIVE'")
    private String status;

    @Column(name = "is_default", columnDefinition = "bit default false")
    private boolean isDefault;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status.name();
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        this.isDefault = aDefault;
    }
}
