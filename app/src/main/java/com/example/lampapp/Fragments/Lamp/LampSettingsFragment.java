package com.example.lampapp.Fragments.Lamp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.lampapp.Controllers.Controller;
import com.example.lampapp.R;

import androidx.fragment.app.Fragment;

public class LampSettingsFragment extends Fragment {

    private Controller controller;
    private TextView tvLampNumber, tvLampStatus, tvHue, tvBrightness, tvLampGroup;
    private Switch lampSwitch;
    private SeekBar progressBarHue, progressBarBrightness;
    private Button buttonAddGroup;

    public int hue; // måste läsa in senaste värdet eller värdet från progressbar innan vi skickar in
    public int brightness; // måste läsa in senaste värdet eller värdet från progressbar innan vi skickar in
    public String exampleString = "{\"on\": true, \"hue\":" + 1 + ", \"sat\":" + 255 + ", \"bri\":" + 1 + "\"}";  // ta inte bort den </3 men värdena 10, 255 osv ska ändras till de vi ändrar
    public String stringToSend = "";

    public boolean lampIsAvailable = false; /////////////// vet inte hur vi ska använda det riktigt

    public LampSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lamp_settings, container, false);
        initComponents(view);
        setLampName();
        setLampStatus();
        setLampHue();
        setLampBrightness();

        getBrightnessProgressbar();
        getHueProgressbar();
        return view;
    }

    private void initComponents(View view) {
        tvLampNumber = view.findViewById(R.id.textViewLampNumber);
        tvLampStatus = view.findViewById(R.id.textViewLampStatus);
        tvHue = view.findViewById(R.id.textViewHue);
        tvBrightness = view.findViewById(R.id.textViewBrightness);
        tvLampGroup = view.findViewById(R.id.textViewLampGroup);
        progressBarHue = view.findViewById(R.id.progressBarHue);
        progressBarBrightness = view.findViewById(R.id.progressBarBrightness);
        buttonAddGroup = view.findViewById(R.id.btnAddGroup);
        buttonAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add to group
            }
        });

        lampSwitch = view.findViewById(R.id.switchOnOff);
        lampSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLampOnOff();
            }
        });
    }

    ////////////// SET METHODS ///////////////

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setLampName() {
        Bundle args = getArguments();
        tvLampNumber.setText(args.getString("nameOfLamp"));
    }

    public void setLampStatus() {
        // lampSwitch.isChecked();
    }

    public void setLampHue() {
        // progressBarHue.setProgress();
    }

    public void setLampBrightness() {
        //  progressBarBrightness.setProgress();
    }

    //////////// GET METHODS /////////////////////////////

    // Vi behöver spara allting - namn, status, hue & brightness
    // Göra det genom sharedPreferences? Eller på ngt annat sätt
    // Tänker att man borde kunna koppla alla värden till namnet pga varje lampa heter något unikt (I guess?..)
    // hämtar lampNamnet (setLampName) genom Bundle men vet inte om det blir lika smidigt för att spara..
    // och tänker att det blir lättare att hålla koll om man har en åtskild GET & SET metod för varje värde..

    public void getLampName() {
        // kanske borde returnera String ändå..
        // eller så skippar vi denna då den hämtas in i setLampName redan. Kan koppla till variabel om de behövs.
    }

    public void getLampStatus() {
        // hämta aktuellt eller senast sparade
    }

    public void getLampHue() {
        // hue = hämta aktuellt värde eller senast sparade
    }

    public void getLampBrightness() {
        // brightness = hämta aktuellt värde eller senast sparade
    }

    ///////////////////////////////////////////////////////

    public void checkLampOnOff() {
        if (lampSwitch.isChecked()) {
            tvLampStatus.setText(lampSwitch.getTextOn());
            // här ska vi spara/uppdatera varje gång värdet ändras
        } else {
            tvLampStatus.setText(lampSwitch.getTextOff());
            // här ska vi spara/uppdatera varje gång värdet ändras
        }
    }

    public void updateStringToSend(String changedValue) {
        // blir nog fucked eftersom vi inte vet om det är STATUS, HUE eller BRIGHTNESS vi skickar in
        // ha 3st seperade metoder? idk..
    }

    public void getBrightnessProgressbar() {
        progressBarBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvBrightness.setText("BRIGHTNESS: " + progressBarBrightness.getProgress());
                brightness = progressBarBrightness.getProgress();
                // här ska vi spara/uppdatera varje gång värdet ändras
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void getHueProgressbar() {
        progressBarHue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvHue.setText("HUE: " + progressBarHue.getProgress());
                hue = progressBarHue.getProgress();
                // här ska vi spara/uppdatera varje gång värdet ändras
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
