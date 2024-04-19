package it.magical.magicam;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import it.magical.magicam.utils.MagiCamPagerAdapter;

public class HomeActivity extends AppCompatActivity {

    private ViewPager2 pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.pager = this.findViewById(R.id.pager);
        this.pager.setAdapter(new MagiCamPagerAdapter(this));
    }
}