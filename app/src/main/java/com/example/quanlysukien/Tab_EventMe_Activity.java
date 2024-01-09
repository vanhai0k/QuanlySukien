package com.example.quanlysukien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.quanlysukien.Fragment.Chat_Fragment;
import com.example.quanlysukien.Fragment.EventMe_Fragment;
import com.example.quanlysukien.Fragment.Event_Fragment;
import com.example.quanlysukien.Fragment.Home_Fragment;
import com.example.quanlysukien.Fragment.Setting_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Tab_EventMe_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_event_me);
        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);

        Bundle bundle = new Bundle();
        Home_Fragment homeFragment = new Home_Fragment();
        homeFragment.setArguments(bundle);

        Setting_Fragment settingFragment = new Setting_Fragment();
        settingFragment.setArguments(bundle);

        EventMe_Fragment eventMeFragment = new EventMe_Fragment();
        eventMeFragment.setArguments(bundle);

        Event_Fragment eventFragment = new Event_Fragment();
        eventFragment.setArguments(bundle);

        Chat_Fragment chatFragment = new Chat_Fragment();
        chatFragment.setArguments(bundle);

        replaceFragment(new EventMe_Fragment());

        navigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    replaceFragment(homeFragment);
                } else if (itemId == R.id.nav_event) {
                    replaceFragment(eventFragment);
                } else if (itemId == R.id.nav_eventme) {
                    replaceFragment(eventMeFragment);
                }else if (itemId == R.id.nav_chat) {
                    replaceFragment(chatFragment);
                } else if (itemId == R.id.nav_setting) {
                    replaceFragment(settingFragment);
                }

                return true;
            }
        });

    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}