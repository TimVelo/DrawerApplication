package com.orangelinephoto.drawerapplication;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by tvelo on 5/28/2017.
 */

public abstract class Record implements Serializable {
    protected static final long serialVersionUID = 1L;
    private UUID id;
    private UUID userSchemaID;
    private UUID createdBy;
    private UUID modifiedBy;
    private Date createdOn;
    private Date modifiedOn;

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
        setModifiedBy(createdBy);
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
        setModifiedOn(createdOn);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(UUID modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public UUID getUserSchemaID() {
        return userSchemaID;
    }

    public void setUserSchemaUUID(UUID id) {
        this.userSchemaID = id;
    }
}
