package com.laurensius_dede_suhardiman.cctvjalanraya.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.developer.kalert.KAlertDialog;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.DeviceInfo;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.TracksInfo;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.gson.Gson;
import com.laurensius_dede_suhardiman.cctvjalanraya.R;
import com.laurensius_dede_suhardiman.cctvjalanraya.adapter.CCTVPointAdapter;
import com.laurensius_dede_suhardiman.cctvjalanraya.controller.AppController;
import com.laurensius_dede_suhardiman.cctvjalanraya.model.CCTVPoint;
import com.laurensius_dede_suhardiman.cctvjalanraya.utilities.CustomListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {

    Intent intentIn;
    private static TextView tvJudul;
    private RecyclerView rvPoint;
    private CCTVPointAdapter cctvPointAdapter;
    private RecyclerView.LayoutManager lmrvPoint;
    private List<CCTVPoint> cctvPointList;

    private StyledPlayerView spvVideo;
    private static ExoPlayer player;

    private Boolean isVideoFullscreen = false;

    Player.Listener listener = new Player.Listener() {
        @Override
        public void onEvents(Player player, Player.Events events) {
            Player.Listener.super.onEvents(player, events);
        }

        @Override
        public void onTimelineChanged(Timeline timeline, int reason) {
            Player.Listener.super.onTimelineChanged(timeline, reason);
        }

        @Override
        public void onMediaItemTransition(@Nullable MediaItem mediaItem, int reason) {
            Player.Listener.super.onMediaItemTransition(mediaItem, reason);
        }

        @Override
        public void onTracksInfoChanged(TracksInfo tracksInfo) {
            Player.Listener.super.onTracksInfoChanged(tracksInfo);
        }

        @Override
        public void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
            Player.Listener.super.onMediaMetadataChanged(mediaMetadata);
        }

        @Override
        public void onPlaylistMetadataChanged(MediaMetadata mediaMetadata) {
            Player.Listener.super.onPlaylistMetadataChanged(mediaMetadata);
        }

        @Override
        public void onIsLoadingChanged(boolean isLoading) {
            Player.Listener.super.onIsLoadingChanged(isLoading);
        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
            Player.Listener.super.onLoadingChanged(isLoading);
        }

        @Override
        public void onAvailableCommandsChanged(Player.Commands availableCommands) {
            Player.Listener.super.onAvailableCommandsChanged(availableCommands);
        }

        @Override
        public void onTrackSelectionParametersChanged(TrackSelectionParameters parameters) {
            Player.Listener.super.onTrackSelectionParametersChanged(parameters);
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            Player.Listener.super.onPlayerStateChanged(playWhenReady, playbackState);
        }

        @Override
        public void onPlaybackStateChanged(int playbackState) {
            Player.Listener.super.onPlaybackStateChanged(playbackState);
            if(playbackState == Player.STATE_ENDED){
                player.stop();
            }
        }

        @Override
        public void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {
            Player.Listener.super.onPlayWhenReadyChanged(playWhenReady, reason);
        }

        @Override
        public void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {
            Player.Listener.super.onPlaybackSuppressionReasonChanged(playbackSuppressionReason);
        }

        @Override
        public void onIsPlayingChanged(boolean isPlaying) {
            Player.Listener.super.onIsPlayingChanged(isPlaying);
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {
            Player.Listener.super.onRepeatModeChanged(repeatMode);
        }

        @Override
        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
            Player.Listener.super.onShuffleModeEnabledChanged(shuffleModeEnabled);
        }

        @Override
        public void onPlayerError(PlaybackException error) {
            Player.Listener.super.onPlayerError(error);
        }

        @Override
        public void onPlayerErrorChanged(@Nullable PlaybackException error) {
            Player.Listener.super.onPlayerErrorChanged(error);
        }

        @Override
        public void onPositionDiscontinuity(int reason) {
            Player.Listener.super.onPositionDiscontinuity(reason);
        }

        @Override
        public void onPositionDiscontinuity(Player.PositionInfo oldPosition, Player.PositionInfo newPosition, int reason) {
            Player.Listener.super.onPositionDiscontinuity(oldPosition, newPosition, reason);
        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            Player.Listener.super.onPlaybackParametersChanged(playbackParameters);
        }

        @Override
        public void onSeekBackIncrementChanged(long seekBackIncrementMs) {
            Player.Listener.super.onSeekBackIncrementChanged(seekBackIncrementMs);
        }

        @Override
        public void onSeekForwardIncrementChanged(long seekForwardIncrementMs) {
            Player.Listener.super.onSeekForwardIncrementChanged(seekForwardIncrementMs);
        }

        @Override
        public void onMaxSeekToPreviousPositionChanged(long maxSeekToPreviousPositionMs) {
            Player.Listener.super.onMaxSeekToPreviousPositionChanged(maxSeekToPreviousPositionMs);
        }

        @Override
        public void onAudioSessionIdChanged(int audioSessionId) {
            Player.Listener.super.onAudioSessionIdChanged(audioSessionId);
        }

        @Override
        public void onAudioAttributesChanged(AudioAttributes audioAttributes) {
            Player.Listener.super.onAudioAttributesChanged(audioAttributes);
        }

        @Override
        public void onVolumeChanged(float volume) {
            Player.Listener.super.onVolumeChanged(volume);
        }

        @Override
        public void onSkipSilenceEnabledChanged(boolean skipSilenceEnabled) {
            Player.Listener.super.onSkipSilenceEnabledChanged(skipSilenceEnabled);
        }

        @Override
        public void onDeviceInfoChanged(DeviceInfo deviceInfo) {
            Player.Listener.super.onDeviceInfoChanged(deviceInfo);
        }

        @Override
        public void onDeviceVolumeChanged(int volume, boolean muted) {
            Player.Listener.super.onDeviceVolumeChanged(volume, muted);
        }

        @Override
        public void onVideoSizeChanged(VideoSize videoSize) {
            Player.Listener.super.onVideoSizeChanged(videoSize);
        }

        @Override
        public void onSurfaceSizeChanged(int width, int height) {
            Player.Listener.super.onSurfaceSizeChanged(width, height);
        }

        @Override
        public void onRenderedFirstFrame() {
            Player.Listener.super.onRenderedFirstFrame();
        }

        @Override
        public void onCues(List<Cue> cues) {
            Player.Listener.super.onCues(cues);
        }

        @Override
        public void onMetadata(Metadata metadata) {
            Player.Listener.super.onMetadata(metadata);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        tvJudul = findViewById(R.id.tv_judul);
        player = new ExoPlayer.Builder(VideoActivity.this).build();
        player.addListener(listener);
        player.setRepeatMode(Player.REPEAT_MODE_OFF);
        spvVideo = findViewById(R.id.spv_cctv);
        spvVideo.setShowFastForwardButton(false);
        spvVideo.setShowNextButton(false);
        spvVideo.setShowPreviousButton(false);
        spvVideo.setShowRewindButton(false);
        spvVideo.setPlayer(player);
        spvVideo.setControllerOnFullScreenModeChangedListener(new StyledPlayerControlView.OnFullScreenModeChangedListener() {
            @Override
            public void onFullScreenModeChanged(boolean isFullScreen) {
                if(isFullScreen){
                    isVideoFullscreen = true;
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    removeUI();
                }else{
                    isVideoFullscreen = false;
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                    attachUI();
                }
            }
        });
        rvPoint = findViewById(R.id.rv_cctv_point);
        rvPoint.addOnItemTouchListener(new CustomListener(VideoActivity.this, new CustomListener.OnItemClickListener() {
            @Override
            public void onItemClick(View childVew, int childAdapterPosition) {
               reInitExoPlayer(cctvPointList.get(childAdapterPosition).getUrlStream(),cctvPointList.get(childAdapterPosition).getNamaPoint());
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        intentIn = getIntent();
        loadPoint(intentIn.getStringExtra("idRegion"));
    }

    void loadPoint(String idRegion){
    String requestTag = "load_point";
    String url = "https://cctv-jalan-raya.laurensius-dede-suhardiman.com/api/get_point_list/" + idRegion;
    final KAlertDialog pDialog = new KAlertDialog(VideoActivity.this,KAlertDialog.PROGRESS_TYPE);
    pDialog.setCancelable(false);
    pDialog.setTitleText("Loading");
    pDialog.setContentText("Loading list CCTV . . .");
    pDialog.show();
    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
            url, null,
            new Response.Listener<JSONObject>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(JSONObject response) {
                    pDialog.dismiss();
                    Log.d("Debug",response.toString());
                    try {
                        int code = response.getInt("code");
                        JSONArray body = response.getJSONArray("body");
                        cctvPointList = new ArrayList<>();
                        for(int x=0;x<body.length();x++){
                            Gson gson = new Gson();
                            String strObj = String.valueOf(body.getJSONObject(x));
                            CCTVPoint cctvPoint = gson.fromJson(strObj,CCTVPoint.class);
                            cctvPointList.add(cctvPoint);
                        }

                        rvPoint.setAdapter(null);
                        rvPoint.setHasFixedSize(true);
                        lmrvPoint = new LinearLayoutManager(VideoActivity.this);
                        rvPoint.setLayoutManager(lmrvPoint);
                        cctvPointAdapter = new CCTVPointAdapter(VideoActivity.this,cctvPointList);
                        cctvPointAdapter.notifyDataSetChanged();
                        rvPoint.setAdapter(cctvPointAdapter);
                        reInitExoPlayer(cctvPointList.get(0).getUrlStream(),cctvPointList.get(0).getNamaPoint());
                        player.play();
                    }catch (JSONException ex){
                        new KAlertDialog(VideoActivity.this,KAlertDialog.ERROR_TYPE)
                        .setTitleText("Error")
                        .setContentText(ex.getMessage())
                        .setConfirmText("OK")
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog kAlertDialog) {
                                kAlertDialog.dismiss();
                            }
                        }).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
                }
            });
        AppController.getInstance().addToRequestQueue(jsonObjReq, requestTag);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(player != null){
            player.stop();
            player.release();
        }
    }

    @Override
    public void onBackPressed() {
        if(isVideoFullscreen){
            isVideoFullscreen = false;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            attachUI();
        }else{
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    public static void reInitExoPlayer(String url,String judul){
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(url));
        player.setMediaItem(mediaItem);
        player.prepare();
        VideoActivity.tvJudul.setText(judul);
    }

    void removeUI(){
        View viewComponnet = getWindow().getDecorView();
        viewComponnet.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }

    void attachUI(){
        View viewComponnet = getWindow().getDecorView();
        viewComponnet.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_VISIBLE
        );
    }
}