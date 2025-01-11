package com.example.weatherapplication20;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView groupRecyclerView;
    private GroupAdapter groupAdapter;
    private List<Group> groupList;
    private List<Group> filteredList; // For filtering
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        ImageView searchIcon = findViewById(R.id.searchIcon);
        ImageView notificationIcon = findViewById(R.id.notificationIcon);
        searchView = findViewById(R.id.searchView); // Initialize SearchView

        // Set up RecyclerView
        groupRecyclerView = findViewById(R.id.groupRecyclerView);
        groupRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize group list and adapter
        groupList = new ArrayList<>();
        groupList.add(new Group("Save The Planet", 50, R.drawable.group_avatar_1));
        groupList.add(new Group("Flood Updates", 30, R.drawable.group_avatar_2));
        groupList.add(new Group("Anti-Fossil Fuel Movement!", 25, R.drawable.group_avatar_3));
        groupList.add(new Group("Go Greener", 25, R.drawable.group_avatar_4));
        groupList.add(new Group("Flat Earthers", 25, R.drawable.group_avatar_5));

        filteredList = new ArrayList<>(groupList); // Initially, all groups are visible
        groupAdapter = new GroupAdapter(this, filteredList); // Pass context as the first argument
        groupRecyclerView.setAdapter(groupAdapter);



        // Search icon functionality
        searchIcon.setOnClickListener(v -> {
            if (searchView.getVisibility() == View.GONE) {
                searchView.setVisibility(View.VISIBLE);
            } else {
                searchView.setVisibility(View.GONE);
                searchView.setQuery("", false);
                resetFilter();
            }
        });

        // SearchView query listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterGroups(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterGroups(newText);
                return true;
            }
        });
    }

    private void filterGroups(String query) {
        filteredList.clear();
        for (Group group : groupList) {
            if (group.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(group);
            }
        }
        groupAdapter.notifyDataSetChanged();
    }

    private void resetFilter() {
        filteredList.clear();
        filteredList.addAll(groupList);
        groupAdapter.notifyDataSetChanged();
    }
}
