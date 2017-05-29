package com.orangelinephoto.drawerapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by tvelo on 5/22/2017.
 * MAINTAINS MAIN DRAWER
 */

public abstract class DrawerActivity extends AppCompatActivity
        implements AccountHeader.OnAccountHeaderListener,
        Drawer.OnDrawerItemClickListener,
        Drawer.OnDrawerListener,
        FloatingSearchView.OnLeftMenuClickListener,
        FloatingSearchView.OnSearchListener,
        FloatingSearchView.OnFocusChangeListener,
        FloatingSearchView.OnMenuItemClickListener,
        ActionBar.OnMenuVisibilityListener, DrawerImageLoader.IDrawerImageLoader {
    static final int HOME_ID = 1;
    static final int ACCOUNT_ID = 21;
    private static final String TAG = DrawerActivity.class.getCanonicalName();
    private static final int REQUEST_ACCESS = 63241;
    protected AccountHeader accountHeader = null;
    protected Drawer drawer = null;
    private SchemaPermission currentSchema;

    private List<SchemaPermission> userSchemas;
    private UserModel user;
    private FloatingSearchView mSearchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayout());
        // creates dummy login options. Usually would be handled by firebase / remote API
        createTestSchemaList();
        DrawerImageLoader.init(this);
        accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.dark_material)
                .withSavedInstance(savedInstanceState)
                .withProfileImagesClickable(true)
                .withCloseDrawerOnProfileListClick(true)
                .withSelectionListEnabledForSingleProfile(true)
                .withOnAccountHeaderListener(this)
                .withTextColor(ContextCompat.getColor(this, R.color.md_white_1000))
                .build();
        //Create the drawer
        //Create the drawer
        drawer = new DrawerBuilder(this)
                .withOnDrawerListener(this)
                .withHasStableIds(true) // .withItemAnimator(new AlphaCrossFadeAnimator())
                .withAccountHeader(accountHeader) //set the AccountHeader we created earlier for the header
                .withOnDrawerItemClickListener(this)
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(false)
                .withCloseOnClick(true)
                .withDrawerWidthDp(320)
                .build();
        drawer.addItem(new PrimaryDrawerItem()
                .withName("TEST")
                .withIcon(MaterialDesignIconic.Icon.gmi_home)
                .withSelectable(false)
                .withIdentifier(1));

        hasLogin(user);
        mSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        mSearchView.setOnLeftMenuClickListener(this);
        mSearchView.setOnSearchListener(this);
        mSearchView.setOnMenuItemClickListener(this);
        mSearchView.setOnFocusChangeListener(this);
    }

    private void createTestSchemaList() {
        userSchemas = new ArrayList<>();
        // create "this users" login info
        SchemaPermission test1 = new SchemaPermission();
        test1.setPermissionID(UUID.randomUUID());
        test1.setOwnerDisplayName("Default User");
        test1.setOwnerEmail("userA@dummy.null");
        test1.setOwnerPhotoUrl("https://randomuser.me/api/portraits/thumb/men/92.jpg");
        test1.setOwnerUUID(UUID.fromString("54AA8E9A-3018-11E7-AFB1-204747C527EC"));

        test1.setGrantedDisplayName("Default User");
        test1.setGrantedEmail("userA@dummy.null");
        test1.setGrantedPhotoUrl("https://randomuser.me/api/portraits/thumb/men/92.jpg");
        test1.setGrantedUserUUID(UUID.fromString("54AA8E9A-3018-11E7-AFB1-204747C527EC"));

        test1.setGrantedSchemaUUID(UUID.fromString("C3ADF51C-3017-11E7-AFB1-204747C527EC"));

        //create a "switchable" user
        SchemaPermission test2 = new SchemaPermission();
        test2.setPermissionID(UUID.randomUUID());
        test2.setOwnerDisplayName("Other User");
        test2.setOwnerEmail("userB@dummy.null");
        test2.setOwnerPhotoUrl("https://randomuser.me/api/portraits/thumb/women/49.jpg");
        test2.setOwnerUUID(UUID.fromString("198D1C6C-FD4B-4C2D-B6A9-1A23CE606BA9"));

        test2.setGrantedDisplayName("Default User");
        test2.setGrantedEmail("userA@dummy.null");
        test2.setGrantedPhotoUrl("https://randomuser.me/api/portraits/thumb/men/92.jpg");
        test2.setGrantedUserUUID(UUID.fromString("54AA8E9A-3018-11E7-AFB1-204747C527EC"));

        test2.setGrantedSchemaUUID(UUID.fromString("60569AB5-3018-11E7-AFB1-204747C527EC"));

        userSchemas.add(test1);
        userSchemas.add(test2);

        user = new UserModel();
        user.setId(UUID.fromString("54AA8E9A-3018-11E7-AFB1-204747C527EC"));
        user.setActive(true);
        user.setDisplayName("Default User");
        user.setUserSchemaUUID(UUID.fromString("C3ADF51C-3017-11E7-AFB1-204747C527EC"));
        user.setEmailAddress("userA@dummy.null");
        user.setPhotoUrl("https://randomuser.me/api/portraits/thumb/men/92.jpg");
        user.setAvailableSchemas(userSchemas);
        setSchema(user.getTargetSchemaID());
    }

    @Override
    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
        if (profile instanceof ProfileDrawerItem && ((ProfileDrawerItem) profile).getTag() instanceof UUID) {
            if (accountHeader.getProfiles() != null) {
                UUID uuid = (UUID) ((ProfileDrawerItem) profile).getTag();

                setSchema(uuid);
                accountHeader.setActiveProfile(profile, false);
            }
        } // NOTE: HANDLE OTHER AUTH HEADER ACTIVITIES;
        return false;
    }


    protected void closeDrawer() {
        if (drawer != null && drawer.isDrawerOpen())
            drawer.closeDrawer();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        drawer = null;
    }

    protected void needsLogin() {
        setupHeaderIcons(null);
    }

    protected void hasLogin(@NonNull UserModel userModel) {
        try {
            setupHeaderIcons(userModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void showHome();

    @LayoutRes
    protected abstract int getActivityLayout();

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

        //check if the drawerItem is set.
        //there are different reasons for the drawerItem to be null
        //--> click on the header
        //--> click on the footer
        //those items don't contain a drawerItem
        if (drawerItem != null) {
            switch ((int) drawerItem.getIdentifier()) {
                case HOME_ID:
                    startActivity(new Intent(DrawerActivity.this, TestBasicActivity.class));
                    return false;
                default:
                    Log.e(TAG, "MISSING ACTION: " + drawerItem.getIdentifier());
            }
        }
        closeDrawer();
        return false;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("INSTANCE_SAVE", outState.toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("INSTANCE_RESTORE", savedInstanceState.toString());
    }

    protected void setupHeaderIcons(@Nullable UserModel userModel) {
        List<IProfile> profiles = new LinkedList<>();
        if (accountHeader != null) {
            profiles.add(0, new ProfileSettingDrawerItem()
                    .withName("Manage Permissions")
                    .withDescription("Manage who can see your things")
                    .withSelectable(false)
                    .withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_lock))
                    .withIdentifier(REQUEST_ACCESS));
            profiles.add(1, new ProfileSettingDrawerItem()
                    .withName("Manage Account")
                    .withDescription("Change your authentication settings")
                    .withSelectable(false)
                    .withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_settings))
                    .withIdentifier(ACCOUNT_ID));
            ProfileDrawerItem currentProfile = null;
            if (userModel != null && userModel.getAvailableSchemas() != null && userModel.getAvailableSchemas().size() > 0) {
                for (SchemaPermission as : userModel.getAvailableSchemas()) {
                    if (as.getTargetSchemaID().equals(getCurrentSchema().getTargetSchemaID())) {
                        currentProfile = new ProfileDrawerItem()
                                .withName(as.getOwnerDisplayName() + " as _" + UserModel.getSecurityName(as.getPermissionLevel()).toLowerCase())
                                .withIcon(as.getOwnerPhotoUrl())
                                .withEmail(UserModel.getSecurityDescription(as.getPermissionLevel()))
                                .withSetSelected(true)
                                .withTag(as.getTargetSchemaID())
                                .withNameShown(true);
                        profiles.add(currentProfile);
                    } else {
                        profiles.add(new ProfileDrawerItem()
                                .withName(as.getOwnerDisplayName() + " as _" + UserModel.getSecurityName(as.getPermissionLevel()).toLowerCase())
                                .withIcon(as.getOwnerPhotoUrl())
                                .withSetSelected(false)
                                .withEmail(UserModel.getSecurityDescription(as.getPermissionLevel()))
                                .withTag(as.getTargetSchemaID()));
                    }
                    if (BuildConfig.DEBUG)
                        if (as.getTargetSchemaID().equals(getCurrentSchema().getTargetSchemaID()))
                            Log.d("ProfileHeader", "Default User:" + as.getOwnerDisplayName());


                }
            }
            accountHeader.setProfiles(profiles);
            if (currentProfile != null)
                accountHeader.setActiveProfile(currentProfile, true);
            else
                Log.e("Drawer", "No profile", new RuntimeException(new UnknownError("current profile is null")));
        }
    }

    @Override
    public void onMenuVisibilityChanged(boolean isVisible) {
        if (drawer != null)
            if (isVisible)
                drawer.openDrawer();
            else
                drawer.closeDrawer();
    }

    @Override
    public void set(ImageView imageView, Uri uri, Drawable placeholder) {
        Picasso.with(DrawerActivity.this).load(uri).into(imageView);

    }

    @Override
    public void set(ImageView imageView, Uri uri, Drawable placeholder, String tag) {
        Picasso.with(DrawerActivity.this).load(uri).into(imageView);
    }

    @Override
    public void cancel(ImageView imageView) {
        imageView.setImageBitmap(null);
        Picasso.with(imageView.getContext()).cancelRequest(imageView);
    }

    @Override
    public Drawable placeholder(Context ctx) {
        return DrawerUIUtils.getPlaceHolder(ctx);
    }

    @Override
    public Drawable placeholder(Context ctx, String tag) {
        if (DrawerImageLoader.Tags.PROFILE.name().equals(tag)) {
            return DrawerUIUtils.getPlaceHolder(ctx);
        } else if (DrawerImageLoader.Tags.ACCOUNT_HEADER.name().equals(tag)) {
            return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(com.mikepenz.materialdrawer.R.color.primary).sizeDp(56);
        } else {
            return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(R.color.md_red_500).sizeDp(56);
        }
    }

    public void setSchema(UUID schemaId) {
        for (SchemaPermission sp : userSchemas) {
            if (sp.getTargetSchemaID().equals(schemaId)) {
                this.currentSchema = sp;
            }
        }
    }

    public SchemaPermission getCurrentSchema() {
        return currentSchema;
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        if (mSearchView != null) mSearchView.openMenu(true);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        if (mSearchView != null) mSearchView.closeMenu(true);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onMenuOpened() {

        if (drawer != null) drawer.openDrawer();
    }

    @Override
    public void onMenuClosed() {
        if (drawer != null) drawer.closeDrawer();
    }

    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (mSearchView != null) mSearchView.setSearchBarTitle(title);
    }

    @Override
    public void onSearchAction(String currentQuery) {
        setTitle(currentQuery);
    }

    @Override
    public void onFocus() {
        //  if (mSearchView != null) mSearchView.swapSuggestions(null);
    }

    @Override
    public void onFocusCleared() {
        mSearchView.setSearchBarTitle(getString(R.string.app_name));
        //you can also set setSearchText(...) to make keep the query there when not focused and when focus returns
        //mSearchView.setSearchText(searchSuggestion.getBody());
    }

    @Override
    public void onActionMenuItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.clear_btn) {
            setTitle(getString(R.string.app_name));
            mSearchView.closeMenu(true);
        }
    }
}