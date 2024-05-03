package it.magical.magicam.shared;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import it.magical.magicam.shared.databinding.FragmentDashboardBinding;
import it.magical.magicam.shared.net.MagiCamControl;
import it.magical.magicam.shared.net.NetworkManager;

public class DashboardPageFragment extends Fragment {

    private final MagiCamControl control = NetworkManager.getI().getControl();

    public final MutableLiveData<Boolean> lightsState = control.getLightsState();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentDashboardBinding binding = DataBindingUtil.setContentView(this.getActivity(), it.magical.magicam.shared.R.layout.fragment_dashboard);

        binding.setLifecycleOwner(this);

        binding.setLightsState(lightsState);

        ImageButton b = this.getActivity().findViewById(R.id.toggleLightsButton);
        b.setOnClickListener((a) ->
                control.setLightsState(!control.getLightsState().getValue()));
    }
}
