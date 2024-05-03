package it.magical.magicam.shared.net;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.lang.reflect.Method;
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

    public boolean checkHotspotState(WifiManager wifiManager) {
        try {
            Method method = wifiManager.getClass().getDeclaredMethod("getWifiApState");
            method.setAccessible(true);
            // 11 = OFF
            // 13 = ON
            int actualState = (Integer) method.invoke(wifiManager, (Object[]) null);
            return actualState == 13;
        } catch (Exception e) {
            // TODO: Proper logging
            e.printStackTrace();
        }
        return false;
    }

    public void startDiscoveryServer() {
        discoveryServer = new DiscoveryServer();
        discovered.observeForever(disc -> {
            if (disc) {
                control.start();
                control.getInfo();
            }
        });
        discoveryServer.setDiscoveryListener(() ->
                new Handler(Looper.getMainLooper()).post(() -> discovered.setValue(true)));
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
