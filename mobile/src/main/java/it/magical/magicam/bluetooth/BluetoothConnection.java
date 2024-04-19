package it.magical.magicam.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.UUID;

public class BluetoothConnection {
    private static BluetoothConnection instance;

    public static BluetoothConnection getInstance() {
        if (instance == null) instance = new BluetoothConnection();

        return instance;
    }

    BluetoothManager manager;
    BluetoothAdapter adapter;

    private BluetoothConnection() {}

    private void connect(Context context, BluetoothDevice device) {
        if (context.checkCallingOrSelfPermission(Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) return;

        try {
            BluetoothSocket sock = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString("3e9b1956-fe8b-11ee-83c5-0712a42c420c"));
            sock.connect();
            char[] buf = new char[50];
            BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            br.read(buf, 0, 50);
            sock.close();
            Toast.makeText(context, new String(buf), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "IOException :(", Toast.LENGTH_SHORT).show();
        }
    }

    public void init(Context context) {
        this.manager = context.getSystemService(BluetoothManager.class);
        this.adapter = this.manager.getAdapter();

        if (context.checkCallingOrSelfPermission(Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) return;

        if (!this.adapter.isEnabled()) {
            this.adapter.enable();
        }

        for (BluetoothDevice d : this.adapter.getBondedDevices()) {
            if (d.getName().equals("magicam")) {
                connect(context, d);
                break;
            }
        }
    }
}
