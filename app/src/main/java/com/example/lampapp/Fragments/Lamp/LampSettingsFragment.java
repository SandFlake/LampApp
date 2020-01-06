package com.example.lampapp.Fragments.Lamp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lampapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LampSettingsFragment extends Fragment {

    public LampSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lamp_settings, container, false);
        return view;
    }

}
