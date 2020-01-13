package com.example.lampapp.LampApp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lampapp.LampApp.MQTT.MQTTHelper;
import com.example.lampapp.R;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @Authors Marcel Laska & Sara Dalvig
 */

public class MainActivity extends AppCompatActivity implements BeaconConsumer, MonitorNotifier {
    // Lamp is available / not available
    public TextView textViewLampOne, textViewLampTwo, textViewLampThree;
    // Lamp 1
    public Switch switchLampOne;
    public TextView textViewBrightnessLampOne, textViewHueLampOne;
    public SeekBar seekbarBrightnessLampOne, seekbarHueLampOne;
    public Button buttonLampOne;
    // Lamp 2
    public Switch switchLampTwo;
    public TextView textViewBrightnessLampTwo, textViewHueLampTwo;
    public SeekBar seekbarBrightnessLampTwo, seekbarHueLampTwo;
    public Button buttonLampTwo;
    // Lamp 3
    public Switch switchLampThree;
    public TextView textViewBrightnessLampThree, textViewHueLampThree;
    public SeekBar seekbarBrightnessLampThree, seekbarHueLampThree;
    public Button buttonLampThree;
    // Group
    public Switch switchGroup;
    public TextView textViewBrightnessGroup, textViewHueGroup;
    public SeekBar seekbarBrightnessGroup, seekbarHueGroup;
    public Button buttonGroup;

    // Beacon
    private BeaconManager mBeaconManager;
    private Region beaconOne = new Region("beaconOne", Identifier.parse("0xc9062d443fd0a83c1ae5"),
            Identifier.parse("0x000000000000"), null);
    private Region beaconTwo = new Region("beaconTwo", Identifier.parse("0xeaf9b6ddc0e1c4ee1aa7"),
            Identifier.parse("0x000000000000"), null);
    private Region beaconThree = new Region("beaconThree", Identifier.parse("160a52fffe853d2f9036"),
            Identifier.parse("0x000000000000"), null);

    // MQTT & other
    public MQTTHelper mqttHelper;
    public Boolean[] lampList;
    private String lampStatus;
    private String hueValue, brightnessValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeSystem();
    }

    private void initializeSystem() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        lampList = new Boolean[3];
        initializeUI();
        versionAndPermissionCheck();
        startMqtt();
    }

    public void versionAndPermissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    public void initializeUI() {
        textViewLampOne = findViewById(R.id.textViewLampOne);
        textViewLampTwo = findViewById(R.id.textViewLampTwo);
        textViewLampThree = findViewById(R.id.textViewLampThree);

        switchLampOne = findViewById(R.id.switchLamp1);
        textViewBrightnessLampOne = findViewById(R.id.tvBrightnessLampOne);
        textViewHueLampOne = findViewById(R.id.tvHueLampOne);
        seekbarBrightnessLampOne = findViewById(R.id.seekBarBrightnessLampOne);
        seekbarHueLampOne = findViewById(R.id.seekBarHueLampOne);
        buttonLampOne = findViewById(R.id.buttonLampOne);

        switchLampTwo = findViewById(R.id.switchLampTwo);
        textViewBrightnessLampTwo = findViewById(R.id.tvBrightnessLampTwo);
        textViewHueLampTwo = findViewById(R.id.tvHueLampTwo);
        seekbarBrightnessLampTwo = findViewById(R.id.seekBarBrightnessLampTwo);
        seekbarHueLampTwo = findViewById(R.id.seekBarHueLampTwo);
        buttonLampTwo = findViewById(R.id.buttonLampTwo);

        switchLampThree = findViewById(R.id.switchLampThree);
        textViewBrightnessLampThree = findViewById(R.id.tvBrightnessLampThree);
        textViewHueLampThree = findViewById(R.id.tvHueLampThree);
        seekbarBrightnessLampThree = findViewById(R.id.seekBarBrightnessLampThree);
        seekbarHueLampThree = findViewById(R.id.seekBarHueLampThree);
        buttonLampThree = findViewById(R.id.buttonLampThree);

        switchGroup = findViewById(R.id.switchGroup);
        textViewBrightnessGroup = findViewById(R.id.tvBrightnessGroup);
        textViewHueGroup = findViewById(R.id.tvHueGroup);
        seekbarBrightnessGroup = findViewById(R.id.seekBarBrightnessGroup);
        seekbarHueGroup = findViewById(R.id.seekBarHueGroup);
        buttonGroup = findViewById(R.id.buttonGroup);

        buttonLampOne.setOnClickListener(new ButtonClickListener());
        buttonLampTwo.setOnClickListener(new ButtonClickListener());
        buttonLampThree.setOnClickListener(new ButtonClickListener());
        buttonGroup.setOnClickListener(new ButtonClickListener());
        buttonLampOne.setEnabled(true);
        buttonLampTwo.setEnabled(true);
        buttonLampThree.setEnabled(true);
        buttonGroup.setEnabled(true);
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.equals(buttonLampOne)) {
                getLampStatus(0);
                getHueValue(0);
                getBrightnessValue(0);
                sendToMqtt("lamp/1", createMQTTMessage(lampStatus, hueValue, brightnessValue)); // ska va så
            }

            if (view.equals(buttonLampTwo)) {
                getLampStatus(1);
                getHueValue(1);
                getBrightnessValue(1);
                sendToMqtt("lamp/2", createMQTTMessage(lampStatus, hueValue, brightnessValue));

            }

            if (view.equals(buttonLampThree)) {
                getLampStatus(2);
                getHueValue(2);
                getBrightnessValue(2);
                sendToMqtt("lamp/3", createMQTTMessage(lampStatus, hueValue, brightnessValue));
            }

            if (view.equals(buttonGroup)) {
                getLampStatus(3);
                getHueValue(3);
                getBrightnessValue(3);
                sendToMqtt("lamp/1", createMQTTMessage(lampStatus, hueValue, brightnessValue));
                sendToMqtt("lamp/2", createMQTTMessage(lampStatus, hueValue, brightnessValue));
                sendToMqtt("lamp/3", createMQTTMessage(lampStatus, hueValue, brightnessValue));
            }
        }
    }

    ////////// CREATE LAMP GROUP //////////////////////////

    private void makeGroupActive() {
        Boolean lampIsInGroup = false;
        for (int i = 0; i < 3; i++) {
            if (lampList[i]) {
                lampIsInGroup = true;
                break;
            }
        }
        buttonGroup.setEnabled(lampIsInGroup);
    }

    ////////////// GET LAMP STATUS, HUE & BRIGHTNESS /////////////////////////////

    public String getLampStatus(int lamp) {
        if (lamp == 0) {
            if (switchLampOne.isChecked()) {
                lampStatus = "true";
                switchLampOne.setText("LAMP 1 ON");
            } else {
                lampStatus = "false";
                switchLampOne.setText("LAMP 1 OFF");
            }
        }

        if (lamp == 1) {
            if (switchLampTwo.isChecked()) {
                lampStatus = "true";
                switchLampTwo.setText("LAMP 2 ON");
            } else {
                lampStatus = "false";
                switchLampTwo.setText("LAMP 2 OFF");
            }
        }

        if (lamp == 2) {
            if (switchLampThree.isChecked()) {
                lampStatus = "true";
                switchLampThree.setText("LAMP 3 ON");
            } else {
                lampStatus = "false";
                switchLampThree.setText("LAMP 3 OFF");
            }
        }

        if (lamp == 3) {
            if (switchGroup.isChecked()) {
                lampStatus = "true";
                switchGroup.setText("GROUP ON");
            } else {
                lampStatus = "false";
                switchGroup.setText("GROUP OFF");
            }
        }

        return lampStatus;
    }

    public String getHueValue(int lamp) {
        if (lamp == 0) {
            hueValue = String.valueOf(seekbarHueLampOne.getProgress());
            textViewHueLampOne.setText("HUE:" + hueValue);
        }

        if (lamp == 1) {
            hueValue = String.valueOf(seekbarHueLampTwo.getProgress());
            textViewHueLampTwo.setText("HUE:" + hueValue);
        }

        if (lamp == 2) {
            hueValue = String.valueOf(seekbarHueLampThree.getProgress());
            textViewHueLampThree.setText("HUE:" + hueValue);
        }

        if (lamp == 3) {
            hueValue = String.valueOf(seekbarHueGroup.getProgress());
            textViewHueGroup.setText("HUE:" + hueValue);
        }

        return hueValue;
    }

    public String getBrightnessValue(int lamp) {
        if (lamp == 0) {
            brightnessValue = String.valueOf(seekbarBrightnessLampOne.getProgress());
            textViewBrightnessLampOne.setText("BRIGHTNESS:" + brightnessValue);
        }

        if (lamp == 1) {
            brightnessValue = String.valueOf(seekbarBrightnessLampTwo.getProgress());
            textViewBrightnessLampTwo.setText("BRIGHTNESS:" + brightnessValue);
        }

        if (lamp == 2) {
            brightnessValue = String.valueOf(seekbarBrightnessLampThree.getProgress());
            textViewBrightnessLampThree.setText("BRIGHTNESS:" + brightnessValue);
        }

        if (lamp == 3) {
            brightnessValue = String.valueOf(seekbarBrightnessGroup.getProgress());
            textViewBrightnessGroup.setText("BRIGHTNESS:" + brightnessValue);
        }

        return brightnessValue;
    }


    /////////////////// SEND MQTT-MSG //////////////////////////////

    public String createMQTTMessage(String lampStatus, String hueValue, String brightnessValue) {
        String mqqtMessage = "{\"on\":" + lampStatus + ", \"bri\":" + brightnessValue + ", \"hue\":" + hueValue + "}";
        return mqqtMessage;

    }

    ////////////////////////// MQTT //////////////////////////////////////

    public void sendToMqtt(String topic, String message) {
        mqttHelper.publish(topic, message);
    }

    private void startMqtt() {
        mqttHelper = new MQTTHelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {

            @Override
            public void connectComplete(boolean b, String s) {
                Log.d("mqttConComplete", "MQTT Connection Complete: " + b);
            }

            @Override
            public void connectionLost(Throwable throwable) {
                Log.d("mqttConLost", "MQTT Connection LOST :(");

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Toast.makeText(MainActivity.this, "MSG ARRIVED: " + mqttMessage.toString(), Toast.LENGTH_SHORT).show();
                if (topic.charAt(8) == '1' && lampList[0]) {
                    textViewLampOne.setText(mqttMessage.toString());
                    Toast.makeText(MainActivity.this, "LAMP 1 MESSAGE ARRIVED", Toast.LENGTH_SHORT).show();
                }

                if (topic.charAt(8) == '2' && lampList[1]) {
                    textViewLampOne.setText(mqttMessage.toString());
                }
                if (topic.charAt(8) == '3' && lampList[2]) {
                    textViewLampOne.setText(mqttMessage.toString());
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                // Log.d("MqttDevComplete", "shit is set yall");
            }
        });
    }

    //////////////////////// Beacon Methods ///////////////////////////////
    @Override
    public void onBeaconServiceConnect() {
        mBeaconManager.addMonitorNotifier(this);
        try {
            mBeaconManager.startMonitoringBeaconsInRegion(beaconOne);
            mBeaconManager.startMonitoringBeaconsInRegion(beaconTwo);
            mBeaconManager.startMonitoringBeaconsInRegion(beaconThree);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void didEnterRegion(Region region) {
        Toast.makeText(getApplicationContext(), "DID ENTER REGION?!", Toast.LENGTH_SHORT).show();
        if (region.getId1().equals(beaconOne.getId1())) {
            lampList[0] = true;
            mqttHelper.subscribeToTopic("arduino/1");
            textViewLampOne.setText("LAMP ONE IS AVAILABLE");
            buttonLampOne.setEnabled(true);
            Toast.makeText(this, "LAMP 1 IS HERE", Toast.LENGTH_SHORT).show();
        }

        if (region.getId1().equals(beaconTwo.getId1())) {
            lampList[1] = true;
            mqttHelper.subscribeToTopic("arduino/2");
            textViewLampTwo.setText("LAMP TWO IS AVAILABLE");
            buttonLampTwo.setEnabled(true);
            Toast.makeText(this, "LAMP 2 IS HERE", Toast.LENGTH_SHORT).show();
        }

        if (region.getId1().equals(beaconThree.getId1())) {
            lampList[2] = true;
            mqttHelper.subscribeToTopic("arduino/3");
            textViewLampThree.setText("LAMP THREE IS AVAILABLE");
            buttonLampThree.setEnabled(true);
            Toast.makeText(this, "LAMP 3 IS HERE", Toast.LENGTH_SHORT).show();
        }

        makeGroupActive();
    }

    @Override
    public void didExitRegion(Region region) {
        if (region.getId1().equals(beaconOne.getId1())) {
            lampList[0] = false;
            mqttHelper.unsubscribeToTopic("arduino/1");
            textViewLampOne.setText("LAMP ONE NOT AVAILABLE");
            buttonLampOne.setEnabled(false);
        }

        if (region.getId1().equals(beaconTwo.getId1())) {
            lampList[1] = false;
            mqttHelper.unsubscribeToTopic("arduino/2");
            textViewLampTwo.setText("LAMP TWO NOT AVAILABLE");
            buttonLampTwo.setEnabled(false);
        }

        if (region.getId1().equals(beaconThree.getId1())) {
            lampList[2] = false;
            mqttHelper.unsubscribeToTopic("arduino/3");
            textViewLampThree.setText("LAMP THREE NOT AVAILABLE");
            buttonLampThree.setEnabled(false);
        }

        makeGroupActive();
    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {
    }

    // Android-app states
    // If user goes out of app or locks screen
    public void onResume() {
        super.onResume();
        mBeaconManager = BeaconManager.getInstanceForApplication(this.getApplicationContext());
        mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));
        mBeaconManager.bind(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mBeaconManager.unbind(this);
    }

}