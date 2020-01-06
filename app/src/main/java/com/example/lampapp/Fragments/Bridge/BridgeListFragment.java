package com.example.lampapp.Fragments.Bridge;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lampapp.Controllers.Controller;
import com.example.lampapp.R;


/*
 * A simple {@link Fragment} subclass.
 */
public class BridgeListFragment extends Fragment {

    private ListView listViewBridges;
    private Controller controller;
    private String[] testArray = {"Bridge One", "Bridge Two", "Bridge Three"};

    public BridgeListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bridge_list, container, false);
        initComponents(view);
        populateListView();
        return view;
    }

    public void initComponents(View view) {
        listViewBridges = view.findViewById(R.id.listViewBridgesList);
    }

    // test method to populate listView
    public void populateListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, testArray);
        listViewBridges.setAdapter(adapter);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

}
