package com.example.lampapp.Fragments.Lamp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lampapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LampListFragment extends Fragment {

    private ListView listViewLamps, listViewGroups;
    private String[] testLamp = {"Lamp One", "Lamp Two", "Lamp Three", "Lamp Four", "Lamp 5", "Lamp 666", "lamp 91919", "Lamp 5", "Lamp 666", "lamp 91919"};
    private String[] testGroup = {"Group One", "Group Two", "Group Three", "Group 4", "Group 5", "Group 6", "Group 4", "Group 5", "Group 6"};

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

    // Test method for listView
    public void initList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, testLamp);
        listViewLamps.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, testGroup);
        listViewGroups.setAdapter(adapter2);
    }

}
