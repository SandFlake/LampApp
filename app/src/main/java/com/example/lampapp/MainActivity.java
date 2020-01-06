package com.example.lampapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lampapp.Controllers.Controller;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeSystem();
    }

    private void initializeSystem() {
        new Controller(this);
    }

    public void setFragment(Fragment fragment, boolean backstack) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.container, fragment);
        if (backstack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }
}