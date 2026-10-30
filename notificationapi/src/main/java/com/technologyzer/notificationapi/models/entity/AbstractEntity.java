package com.technologyzer.notificationapi.models.entity;

import lombok.Data;

@Data
public abstract class AbstractEntity {
    private Long createdAt;
    private Long updatedAt;

    public void entityCreated(){
        setCreatedAt(System.currentTimeMillis());
        setUpdatedAt(System.currentTimeMillis());
    }
    public void entityUpdated(){
        setUpdatedAt(System.currentTimeMillis());
    }
}
