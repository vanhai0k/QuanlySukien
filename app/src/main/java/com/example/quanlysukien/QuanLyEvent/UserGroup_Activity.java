package com.example.quanlysukien.QuanLyEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.quanlysukien.R;
import com.example.quanlysukien.ViewPageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class UserGroup_Activity extends AppCompatActivity {

    TabLayout mTabLayout;
    ViewPager2 mViewPage;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_group);

        mTabLayout = findViewById(R.id.tabLayout);
        mViewPage = findViewById(R.id.viewPage);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager(),getLifecycle());
        mViewPage.setAdapter(adapter);

        new TabLayoutMediator(mTabLayout, mViewPage,
                (tab, position) -> {
                    // Set the custom tab title using the getTabTitle method
                    tab.setText(adapter.getTabTitle(position));
                }
        ).attach();
    }
}