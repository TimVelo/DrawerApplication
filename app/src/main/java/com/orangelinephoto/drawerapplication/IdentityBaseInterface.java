package com.orangelinephoto.drawerapplication;

import java.util.UUID;

/**
 * Created by tvelo on 5/28/2017.
 */

public interface IdentityBaseInterface {
    String getDisplayName();

    String getPhotoUrl();

    UUID getTargetSchemaID();
}
