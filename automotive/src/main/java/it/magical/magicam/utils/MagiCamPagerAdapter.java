package it.magical.magicam.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

import it.magical.magicam.CameraPageFragment;
import it.magical.magicam.DashboardPageFragment;
import it.magical.magicam.SettingsPageFragment;

public class MagiCamPagerAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 3;

    private final ArrayList<Fragment> fragments;

    public MagiCamPagerAdapter(FragmentActivity fa) {
        super(fa);

        this.fragments = new ArrayList<>();
        this.fragments.add(new CameraPageFragment());
        this.fragments.add(new DashboardPageFragment());
        this.fragments.add(new SettingsPageFragment());
    }

    @Override
    @NonNull
    public Fragment createFragment(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
