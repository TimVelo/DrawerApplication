package com.orangelinephoto.drawerapplication;

import android.os.Bundle;

public class MainActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void showHome() {

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_main;
    }

}