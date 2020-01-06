package com.example.lampapp.Controllers;

import com.example.lampapp.Fragments.Bridge.BridgeListFragment;
import com.example.lampapp.Fragments.Lamp.LampListFragment;
import com.example.lampapp.Fragments.Lamp.LampSettingsFragment;
import com.example.lampapp.MainActivity;

public class Controller {

    public MainActivity mainActivity;
    private BridgeListFragment bridgeListFragment;
    private LampListFragment lampListFragment;
    private LampSettingsFragment lampSettingsFragment;

    public Controller(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        initComponents();
    }

    public void initComponents() {
        bridgeListFragment = new BridgeListFragment();
        lampListFragment = new LampListFragment();
        mainActivity.setFragment(lampListFragment, false);
    }


}
