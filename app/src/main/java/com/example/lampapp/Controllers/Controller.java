package com.example.lampapp.Controllers;

import android.util.Log;

import com.example.lampapp.Controllers.Helpers.MQQTHelper;
import com.example.lampapp.Fragments.Bridge.BridgeListFragment;
import com.example.lampapp.Fragments.Lamp.LampListFragment;
import com.example.lampapp.Fragments.Lamp.LampSettingsFragment;
import com.example.lampapp.MainActivity;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import androidx.fragment.app.Fragment;

public class Controller {

    private MainActivity mainActivity;
    private MQQTHelper mqttHelper;
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
        lampSettingsFragment = new LampSettingsFragment();
        mainActivity.setFragment(lampSettingsFragment);
        lampSettingsFragment.setController(this);
        lampListFragment.setController(this);
        startMqtt();
    }

    public void setFragment(Fragment fragment) {
        mainActivity.setFragment(fragment);
    }

    ///////////////////// MQQT Methods //////////////////////////////////////

    private void startMqtt() {
        mqttHelper = new MQQTHelper(mainActivity.getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.d("123", "messageArrived: " + mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }
}