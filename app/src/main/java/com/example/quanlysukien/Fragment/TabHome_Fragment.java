package com.example.quanlysukien.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quanlysukien.API.URL;
import com.example.quanlysukien.HomeViewPageAdapter;
import com.example.quanlysukien.R;
import com.example.quanlysukien.ViewPageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TabHome_Fragment extends Fragment {

    TabLayout mTabLayout;
    ViewPager2 mViewPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_home_, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        TextView username = view.findViewById(R.id.username);
        ImageView image = view.findViewById(R.id.image);
        String iamges = sharedPreferences.getString("image",null);
        String names = sharedPreferences.getString("username",null);
        username.setText(names);
        Glide.with(getActivity()).load(URL.image + iamges).into(image);


        mTabLayout = view.findViewById(R.id.tabLayout);
        mViewPage = view.findViewById(R.id.viewPage);

        HomeViewPageAdapter adapter = new HomeViewPageAdapter(getChildFragmentManager(),getLifecycle());
        mViewPage.setAdapter(adapter);

        mViewPage.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);

        new TabLayoutMediator(mTabLayout, mViewPage,
                (tab, position) -> {
                    // Set the custom tab title using the getTabTitle method
                    tab.setText(adapter.getTabTitle(position));
                }
        ).attach();
        mViewPage.setSaveEnabled(false);


        return view;
    }
}