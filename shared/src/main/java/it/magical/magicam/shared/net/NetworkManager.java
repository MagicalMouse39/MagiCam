package it.magical.magicam.shared.net;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class NetworkManager {
    private DiscoveryServer discoveryServer;

    private final MutableLiveData<Boolean> discovered = new MutableLiveData<>();

    private InetAddress magicamAddress;

    private MagiCamControl control;

    private static NetworkManager instance;

    public static NetworkManager getI() {
        if (instance == null) {
            instance = new NetworkManager();
        }

        return instance;
    }

    private NetworkManager() {
        control = new MagiCamControl();
    }

    public void startDiscoveryServer() {
        discoveryServer = new DiscoveryServer();
        discovered.observeForever(disc -> {
            if (disc) {
                new Handler(Looper.getMainLooper()).post(() -> discovered.setValue(true));
                control.start();
            }
        });
        discoveryServer.start();
    }

    public void shutdownDiscoveryServer() {
        if (discoveryServer == null) return;

        discoveryServer.shutdown();
    }

    public InetAddress getMagicamAddress() {
        return magicamAddress;
    }

    public void setMagicamAddress(InetAddress magicamAddress) {
        this.magicamAddress = magicamAddress;
    }

    public MagiCamControl getControl() {
        return control;
    }

    public MutableLiveData<Boolean> getDiscovered() {
        return discovered;
    }
}
