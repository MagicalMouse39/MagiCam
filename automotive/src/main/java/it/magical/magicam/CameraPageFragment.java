package it.magical.magicam;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingConversion;
import androidx.databinding.ObservableBoolean;
import androidx.fragment.app.Fragment;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.DefaultLoadControl;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.LoadControl;
import androidx.media3.exoplayer.rtsp.RtspMediaSource;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.ui.PlayerView;

import it.magical.magicam.shared.net.NetworkManager;

public class CameraPageFragment extends Fragment {
    private final NetworkManager netman = NetworkManager.getI();

    public final ObservableBoolean connected = new ObservableBoolean(netman.hasDiscovered());

    private final Runnable discoveryListener = () -> connected.set(true);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @UnstableApi
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        netman.addDiscoveryListener(discoveryListener);

        MediaSource mediaSource =
                new RtspMediaSource.Factory().createMediaSource(MediaItem.fromUri("rtsp://192.168.53.139:8554/cam.stream"));

        LoadControl loadControl = new DefaultLoadControl.Builder()
                .setBufferDurationsMs(0, 0, 0, 0)
                .build();

        ExoPlayer player = new ExoPlayer.Builder(view.getContext()).setLoadControl(loadControl).build();

        player.setMediaSource(mediaSource);
        player.setPlayWhenReady(true);
        player.prepare();

        PlayerView p = view.findViewById(R.id.player);
        p.setPlayer(player);
    }

    @Override
    public void onDestroyView() {
        netman.removeDiscoveryListener(discoveryListener);

        super.onDestroyView();
    }
}
