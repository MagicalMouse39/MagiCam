package it.magical.magicam.shared.net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

public class ControlClient {
    private void sendCmd(String cmd, String... args) {
        new Thread(() -> {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(NetworkManager.getI().getMagicamAddress(), 6969));

                JSONObject payload = new JSONObject();
                payload.put("command", cmd);
                JSONArray argsArray = new JSONArray();
                for (String arg : args) {
                    argsArray.put(arg);
                }
                payload.put("args", argsArray);
                socket.getOutputStream().write(payload.toString().getBytes());

                byte[] buf = new byte[512];
                if (socket.getInputStream().read(buf) < 0) {
                    throw new IOException();
                }
                JSONObject response = new JSONObject(new String(buf));
                int code = (int) response.get("response");
                if (code != 0) {
                    throw new CommandFailedException(code, (String) response.get("message"));
                }
            } catch (IOException e) {
                // TODO: Proper logging
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO: Proper logging
                e.printStackTrace();
            }
        }).start();
    }

    public void setLightsState(boolean state) {
        sendCmd("lights_state", state ? "on" : "off");
    }
}
