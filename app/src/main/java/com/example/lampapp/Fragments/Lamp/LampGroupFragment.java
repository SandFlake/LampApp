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

/**
 * A simple {@link Fragment} subclass.
 */
public class LampGroupFragment extends Fragment {

    private Controller controller;
    private TextView tvGroupName, tvLampStatusGroup, tvHueGroup, tvBrightnessGroup, tvLampsInGroup, tvLampGroup;
    private Switch lampSwitchGroup;
    private SeekBar progressBarHueGroup, progressBarBrightnessGroup;
    private Button buttonDeleteGroup;
    public String lampsInGroup = "";

    public LampGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_settings, container, false);
        initializeComponents(view);
        setLampNumber();
        setLampStatus();
        setHueProgessbar();
        setBrightnessProgessbar();
        getHueProgressbar();
        getBrightnessProgressbar();
        return view;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void initializeComponents(View view) {
        tvGroupName = view.findViewById(R.id.textViewGroupName);
        tvLampStatusGroup = view.findViewById(R.id.textViewGroupLampStatus);
        tvHueGroup = view.findViewById(R.id.textViewHueGroup);
        tvBrightnessGroup = view.findViewById(R.id.textViewBrightnessGroup);
        tvLampGroup = view.findViewById(R.id.textViewLampGroupName);
        tvLampsInGroup = view.findViewById(R.id.textViewLampsInGroup);
        lampSwitchGroup = view.findViewById(R.id.switchGroupOnOff);
        progressBarBrightnessGroup = view.findViewById(R.id.progressBarBrightnessGroup);
        progressBarHueGroup = view.findViewById(R.id.progressBarHueGroup);
        buttonDeleteGroup = view.findViewById(R.id.btnDeleteGroup);
        buttonDeleteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add to group
            }
        });

        lampSwitchGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLampOnOff();
            }
        });
    }

    public void setLampNumber() {
        tvGroupName.setText("GROUP"); // lägg till lampnr som skickas me när man klickar i listan i lampListFragment
        // eller grupp om de e grupp
    }

    public void addLampsNameToLabel() {
        tvLampsInGroup.setText(lampsInGroup);
    }

    public void setLampStatus() {
        tvLampStatusGroup.setText("LAMPS: OFF"); // om den är på eller av direkt
    }


    public void checkLampOnOff() {
        if (lampSwitchGroup.isChecked()) {
            tvLampStatusGroup.setText(lampSwitchGroup.getTextOn());
            // stäng på lampan
        } else {
            tvLampStatusGroup.setText(lampSwitchGroup.getTextOff());
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
        progressBarBrightnessGroup.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvBrightnessGroup.setText("BRIGHTNESS: " + progressBarBrightnessGroup.getProgress());
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
        progressBarHueGroup.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvHueGroup.setText("HUE: " + progressBarHueGroup.getProgress());
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
