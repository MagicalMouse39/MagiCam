package it.magical.magicam;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.fragment.app.Fragment;

import it.magical.magicam.shared.net.NetworkManager;

public class SettingsPageFragment extends Fragment {
    private final NetworkManager netman = NetworkManager.getI();

    public final ObservableBoolean discovered = new ObservableBoolean(netman.hasDiscovered());
    public final ObservableField<String> magicamAddress = new ObservableField<String>("");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (netman.getMagicamAddress() != null) {
            magicamAddress.set(netman.getMagicamAddress().getHostAddress());
        } else {
            netman.addDiscoveryListener(() -> magicamAddress.set(netman.getMagicamAddress().getHostAddress()));
        }
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}
