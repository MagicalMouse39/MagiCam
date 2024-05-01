package it.magical.magicam.shared.net;

import java.util.ArrayList;
import java.util.List;

public class NetworkManager {
    private final List<Runnable> discoveryListeners;

    private DiscoveryServer discoveryServer;

    private boolean discovered = false;

    private static NetworkManager instance;
    public static NetworkManager getI() {
        if (instance == null) {
            instance = new NetworkManager();
        }

        return instance;
    }

    private NetworkManager() {
        discoveryListeners = new ArrayList<>();
        discoveryListeners.add(() -> discovered = true);
    }

    public void startDiscoveryServer() {
        discoveryServer = new DiscoveryServer();
        discoveryServer.setDiscoveryListener(() -> discoveryListeners.forEach(Runnable::run));
        discoveryServer.start();
    }

    public void shutdownDiscoveryServer() {
        if (discoveryServer == null) return;

        discoveryServer.shutdown();
    }

    public void addDiscoveryListener(Runnable listener) {
        this.discoveryListeners.add(listener);
    }

    public void removeDiscoveryListener(Runnable listener) {
        this.discoveryListeners.remove(listener);
    }

    public boolean hasDiscovered() {
        return discovered;
    }
}
