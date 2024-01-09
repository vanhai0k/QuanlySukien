package com.example.quanlysukien;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.quanlysukien.Fragment_Group.Administrators_Fragment;
import com.example.quanlysukien.Fragment_Group.Confirmation_Fragment;
import com.example.quanlysukien.Fragment_Group.UserGroup_Fragment;

public class ViewPageAdapter extends FragmentStateAdapter {

    public ViewPageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new UserGroup_Fragment();
            case 1:
                return new Administrators_Fragment();
            case 2:
                return new Confirmation_Fragment();
            default:
                return new UserGroup_Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
    public String getTabTitle(int position) {
        switch (position) {
            case 0:
                return "User Group";
            case 1:
                return "Administrators";
            case 2:
                return "Wait for confirmation";
            default:
                return "";
        }
    }
}
