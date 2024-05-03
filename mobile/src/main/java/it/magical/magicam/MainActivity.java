package it.magical.magicam;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;

import it.magical.magicam.shared.databinding.FragmentDashboardBinding;
import it.magical.magicam.shared.net.MagiCamControl;
import it.magical.magicam.shared.net.NetworkManager;

public class MainActivity extends AppCompatActivity {

    private final MagiCamControl control = NetworkManager.getI().getControl();

    public final MutableLiveData<Boolean> lightsState = control.getLightsState();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(it.magical.magicam.shared.R.layout.fragment_dashboard);

        NetworkManager.getI().startDiscoveryServer();

        FragmentDashboardBinding binding = DataBindingUtil.setContentView(this, it.magical.magicam.shared.R.layout.fragment_dashboard);

        binding.setLifecycleOwner(this);

        binding.setLightsState(lightsState);

        Button b = findViewById(it.magical.magicam.shared.R.id.toggleLightsButton);
        b.setOnClickListener((a) ->
                control.setLightsState(!control.getLightsState().getValue()));
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
