package it.magical.magicam.shared.net;

import android.database.Observable;
import android.os.Handler;
import android.os.Looper;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MagiCamControl extends Thread {
    private ControlClient client;

    private final MutableLiveData<Boolean> lightsState = new MutableLiveData<>(false);

    public MagiCamControl() {
        client = new ControlClient();
    }

    private void handleUpdate(String name, String[] val) {
        switch (name) {
            case "lights_state":
                boolean state = val[0].equals("1");
                new Handler(Looper.getMainLooper()).post(() -> lightsState.setValue(state));
                break;
            case "test":
                break;
            default:
                // TODO: Proper logging (unknown command)
        }
    }

    private void handleClient(Socket client) {
        try {
            byte[] buf = new byte[1024];
            if (client.getInputStream().read(buf) < 0) throw new IOException("Failed to read input stream");
            JSONObject o = new JSONObject(new String(buf));
            JSONArray jsonValues = (JSONArray) o.get("values");
            List<String> values = new ArrayList<>();
            for (int i = 0; i < jsonValues.length(); i++) {
                values.add((String) jsonValues.get(i));
            }
            handleUpdate(
                    (String) o.get("name"),
                    values.toArray(new String[0]));
            client.close();
        } catch (IOException e) {
            // TODO: Proper logging
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO: Proper loggin (malformed json)
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();

        try (ServerSocket socket = new ServerSocket()) {
            socket.bind(new InetSocketAddress("0.0.0.0", 6969));
            while (true) {
                try {
                    Socket client = socket.accept();
                    new Thread(() -> handleClient(client)).start();
                } catch (IOException e) {
                    break;
                }
            }
        } catch (IOException e) {
            // TODO: Proper logging
            e.printStackTrace();
        }
    }

    public MutableLiveData<Boolean> getLightsState() {
        return lightsState;
    }

    public void setLightsState(boolean state) {
        client.setLightsState(state);
    }
}
