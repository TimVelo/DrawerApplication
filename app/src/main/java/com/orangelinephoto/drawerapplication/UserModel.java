package com.orangelinephoto.drawerapplication;

/**
 * Created by tvelo on 5/28/2017.
 */


import java.util.List;
import java.util.UUID;

/**
 * Created by tvelo on 3/27/2017.
 */
@SuppressWarnings("unused")
public class UserModel extends Record implements IdentityBaseInterface {
    private static final long serialVersionUID = 1L;
    private String displayName;
    private String emailAddress;
    private String userRoles;
    private String providerID;
    private String oauthUUID;
    private String photoUrl;
    private List<SchemaPermission> availableSchemas;
    private String token;
    private boolean isEmailVerified;
    private Integer appVersion;
    private Integer limit;
    private boolean active;


    public UserModel() {
    }

    public static String getSecurityName(int privID) {
        switch (privID) {
            case 0:
                return "Admin";
            case 1:
                return "User";
            case 2:
                return "Guest";
            default:
                return "Guest";
        }
    }

    public static String getSecurityDescription(int permissionLevel) {
        switch (permissionLevel) {
            case 0:
                return "Can Create, Read, Update & Delete";
            case 1:
                return "Can Create, Read, Update";
            case 2:
                return "Can Read";
            default:
                return "Can Read";
        }
    }

    public SchemaPermission getSchema(UUID targetID) {
        if (availableSchemas != null && availableSchemas.size() > 0) {
            for (SchemaPermission as : availableSchemas) {
                if (as.getTargetSchemaID().equals(targetID)) {
                    return as;
                }
            }
        }
        return null;
    }

    public String getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(String readerUserRoles) {
        this.userRoles = readerUserRoles;
    }

    public String getReaderUserRolesString() {
        return getUserRoles().toString();
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getProviderID() {
        return providerID;
    }

    public void setProviderID(String providerID) {
        this.providerID = providerID;
    }

    public String getOauthUUID() {
        return oauthUUID;
    }

    public void setOauthUUID(String oauthUUID) {
        this.oauthUUID = oauthUUID;
    }

    @Override
    public String getPhotoUrl() {
        if (photoUrl!=null && photoUrl.length()>0)
            return photoUrl;
        else
            return "";
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public UUID getTargetSchemaID() {
        return getUserSchemaID();
    }

    public boolean getIsEmailVerified() {
        return isEmailVerified;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<SchemaPermission> getAvailableSchemas() {
        return availableSchemas;
    }

    public void setAvailableSchemas(List<SchemaPermission> availableSchemas) {
        this.availableSchemas = availableSchemas;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public Integer getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(Integer appVersion) {
        this.appVersion = appVersion;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}