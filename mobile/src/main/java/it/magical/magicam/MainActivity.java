package it.magical.magicam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import it.magical.magicam.bluetooth.BluetoothConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (String perm : new String[] {android.Manifest.permission.BLUETOOTH, android.Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.ACCESS_FINE_LOCATION}) {
            if (this.getApplicationContext().checkCallingOrSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[] { perm },
                        225);
            }
        }

        BluetoothConnection.getInstance().init(this.getApplicationContext());

        setContentView(it.magical.magicam.shared.R.layout.fragment_dashboard);
    }
}