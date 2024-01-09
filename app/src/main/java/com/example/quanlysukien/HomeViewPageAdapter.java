package com.example.quanlysukien;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.quanlysukien.Home.Happenning_Fragment;
import com.example.quanlysukien.Home.Upcoming_Fragment;

public class HomeViewPageAdapter extends FragmentStateAdapter {


    public HomeViewPageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Upcoming_Fragment();
            case 1:
                return new Happenning_Fragment();
            default:
                return new Happenning_Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
    public String getTabTitle(int position) {
        switch (position) {
            case 0:
                return "Upcoming";
            case 1:
                return "Happenning";
            default:
                return "Happenning";
        }
    }
}
