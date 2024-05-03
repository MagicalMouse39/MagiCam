package it.magical.magicam.shared.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class DiscoveryServer extends Thread {
    private static final String MAGICAM_ID = "MagiCam!";

    private ServerSocket sock;

    private Runnable discoveryListener = () -> {};

    public DiscoveryServer() { }

    public void setDiscoveryListener(Runnable listener) {
        discoveryListener = listener;
    }

    @Override
    public void run() {
        super.run();

        try {
            sock = new ServerSocket();
            sock.bind(new InetSocketAddress("0.0.0.0", 6968));
            while (true) {
                try (Socket client = sock.accept()) {
                    client.setSoTimeout(2000);
                    byte[] buf = new byte[8];
                    if (client.getInputStream().read(buf) < 0) continue;
                    if (!new String(buf).equals(MAGICAM_ID)) continue;
                    NetworkManager.getI().setMagicamAddress(client.getInetAddress());
                    sock.close();
                    discoveryListener.run();
                    break;
                } catch (IOException e) {
                    // TODO: Logging
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            // TODO: Logging
            e.printStackTrace();
        }
    }

    public void shutdown() {
        if (sock == null || sock.isClosed()) return;

        try {
            sock.close();
        } catch (IOException e) {
            // TODO: Logging
            e.printStackTrace();
        }
    }
}
