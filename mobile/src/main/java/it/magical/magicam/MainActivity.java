package it.magical.magicam;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;

import java.lang.reflect.Method;

import it.magical.magicam.shared.databinding.FragmentDashboardBinding;
import it.magical.magicam.shared.net.MagiCamControl;
import it.magical.magicam.shared.net.NetworkManager;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (!NetworkManager.getI().checkHotspotState(wifiManager)) {
            Toast.makeText(this, "Please, enable hotspot!", Toast.LENGTH_LONG).show();
        }
        NetworkManager.getI().startDiscoveryServer();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
