package com.linhnv.diary.models.entities;

import com.linhnv.diary.models.bos.StatusEnum;

import javax.persistence.*;

@Entity
@Table(name = "feelings")
public class Feeling {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(500)")
    private String name;

    @Column(columnDefinition = "VARCHAR(20) default 'ACTIVE'")
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status.name();
    }
}
