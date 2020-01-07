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

    public LampSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lamp_settings, container, false);
        initComponents(view);
        setLampNumber();
        setLampStatus();
        setHueProgessbar();
        setBrightnessProgessbar();
        getHueProgressbar();
        getBrightnessProgressbar();
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

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setLampNumber() {
        tvLampNumber.setText("LAMP "); // lägg till lampnr som skickas me när man klickar i listan i lampListFragment
    }

    public void setLampStatus() {
        tvLampStatus.setText("LAMP: OFF"); // om den är på eller av direkt
    }

    public void checkLampOnOff() {
        if (lampSwitch.isChecked()) {
            tvLampStatus.setText(lampSwitch.getTextOn());
            // stäng på lampan
        } else {
            tvLampStatus.setText(lampSwitch.getTextOff());
            // stäng av lampan
        }
    }

    public void setHueProgessbar() {
        // progressBarHue.setProgress(); sätt den till whatever den är/var sist
    }


    public void setBrightnessProgessbar() {
        // sätt den till whatever den är/var sist
    }

    public void getBrightnessProgressbar() {
        progressBarBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvBrightness.setText("BRIGHTNESS: " + progressBarBrightness.getProgress());
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
