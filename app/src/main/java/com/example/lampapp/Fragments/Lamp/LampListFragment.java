package com.example.lampapp.Fragments.Lamp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lampapp.Controllers.Controller;
import com.example.lampapp.R;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LampListFragment extends Fragment {

    private Controller controller;
    private LampSettingsFragment lampSettingsFragment;
    private ListView listViewLamps, listViewGroups;
    private String[] lampList = {"Test Lamp 1", "Test Lamp 2"};
    private String[] groupList = {"Test Group 1", "Test Group 2"};

    public LampListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lamp_list, container, false);
        initComponents(view);
        initList();
        return view;
    }

    public void initComponents(View view) {
        listViewLamps = view.findViewById(R.id.listViewLamps);
        listViewGroups = view.findViewById(R.id.listViewGroups);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    // Test method for listView
    public void initList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, lampList);
        listViewLamps.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, groupList);
        listViewGroups.setAdapter(adapter2);

        listViewLamps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                controller.setFragment(lampSettingsFragment);
            }
        });
    }

}
