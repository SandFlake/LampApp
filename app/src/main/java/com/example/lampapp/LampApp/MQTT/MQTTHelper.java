package com.example.lampapp.LampApp.MQTT;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.lampapp.LampApp.MainActivity;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class MQTTHelper {
    public MqttAndroidClient mqttAndroidClient;

    final String serverURL = "tcp://farmer.cloudmqtt.com:12987";
    final String username = "xbwjslxs";
    final String password = "w72O5sZkZnUW";
    private MainActivity mainActivity;

    public MQTTHelper(Context context) {
        mqttAndroidClient = new MqttAndroidClient(context, serverURL, username);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {

            @Override
            public void connectComplete(boolean b, String s) {
                Log.d("conComplete", "connectComplete: " + b);
                Toast.makeText(mainActivity.getApplicationContext(), "CONNECTION COMPLETE", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void connectionLost(Throwable throwable) {
                Log.d("conLost", "connectionLost: " + throwable);
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.d("msgArr", "Message Arrived: " + mqttMessage.toString());
                Toast.makeText(mainActivity.getApplicationContext(), "MESSAGE ARRIVED: " + mqttMessage.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });

        connect();
    }

    public void setCallback(MqttCallbackExtended callback) {
        mqttAndroidClient.setCallback(callback);
    }

    private void connect() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());

        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.d("serverConnectionFailure", "Failed to connect to: " + serverURL);
                }
            });


        } catch (MqttException ex) {
            ex.printStackTrace();
        }
    }


    public void subscribeToTopic(final String topic) {
        try {
            mqttAndroidClient.subscribe(topic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.w("subSuccess", "Subscribed to topic!!");
                    Toast.makeText(mainActivity.getApplicationContext(), "SUBBED TO TOPIC: " + topic, Toast.LENGTH_SHORT).show();

                }


                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.d("subFailed", "Sub to topic failed: " + exception);
                    Toast.makeText(mainActivity.getApplicationContext(), "FAILED TO SUB TO TOPIC" + topic, Toast.LENGTH_SHORT).show();

                }
            });

        } catch (MqttException ex) {
            ex.printStackTrace();
        }
    }

    public void unsubscribeToTopic(String topic) {
        try {
            mqttAndroidClient.unsubscribe(topic);

        } catch (MqttException ex) {
            System.err.println("Exception whilst subscribing");
            ex.printStackTrace();
        }
    }

    public void publish(String topic, String payload) {
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            mqttAndroidClient.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }

    }
}
