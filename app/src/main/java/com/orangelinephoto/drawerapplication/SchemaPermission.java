package com.orangelinephoto.drawerapplication;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by tvelo on 5/28/2017.
 */

public class SchemaPermission implements Serializable, IdentityBaseInterface {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private UUID permissionID;
    private UUID grantedSchemaID;

    private UUID grantedUserID;
    private String grantedDisplayName;
    private String grantedEmail;
    private String grantedPhotoUrl;

    private int permissionLevel;

    private UUID ownerID;
    private String ownerDisplayName;
    private String ownerEmail;
    private String ownerPhotoUrl;


    private boolean isDefault;

    public SchemaPermission() {

    }

    public UUID getTargetSchemaID() {
        return grantedSchemaID;
    }

    public void setGrantedSchemaUUID(UUID userSchemaID) {
        this.grantedSchemaID = userSchemaID;
    }

    public UUID getGrantedUserID() {
        return grantedUserID;
    }

    public void setGrantedUserUUID(UUID userID) {
        this.grantedUserID = userID;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String userOwnerEmail) {
        this.ownerEmail = userOwnerEmail;
    }

    public UUID getOwnerID() {
        return ownerID;
    }

    public String getOwnerPhotoUrl() {
        return ownerPhotoUrl;
    }

    public void setOwnerPhotoUrl(String userOwnerPhotoUrl) {
        this.ownerPhotoUrl = userOwnerPhotoUrl;
    }

    public UUID getPermissionID() {
        return permissionID;
    }

    public void setPermissionID(UUID permID) {
        this.permissionID = permID;
    }

    public void setOwnerUUID(UUID userOwnerUUID) {
        this.ownerID = userOwnerUUID;
    }

    public String getOwnerDisplayName() {
        return ownerDisplayName;
    }

    public void setOwnerDisplayName(String ownerDisplayName) {
        this.ownerDisplayName = ownerDisplayName;
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(int permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public boolean canWrite() {
        return (permissionLevel == 0 || permissionLevel == 1);
    }

    public boolean canUpdate() {
        return (permissionLevel == 0 || permissionLevel == 1);
    }

    public boolean canDelete() {
        return (permissionLevel == 0);
    }

    @Override
    public String getDisplayName() {
        return getOwnerDisplayName();
    }

    @Override
    public String getPhotoUrl() {
        return getOwnerPhotoUrl();
    }

    public String getGrantedPhotoUrl() {
        return grantedPhotoUrl;
    }

    public void setGrantedPhotoUrl(String grantedPhotoUrl) {
        this.grantedPhotoUrl = grantedPhotoUrl;
    }

    public String getGrantedEmail() {
        return grantedEmail;
    }

    public void setGrantedEmail(String grantedEmail) {
        this.grantedEmail = grantedEmail;
    }

    public String getGrantedDisplayName() {
        return grantedDisplayName;
    }

    public void setGrantedDisplayName(String grantedDisplayName) {
        this.grantedDisplayName = grantedDisplayName;
    }
}