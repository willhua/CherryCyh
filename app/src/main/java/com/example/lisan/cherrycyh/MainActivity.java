package com.example.lisan.cherrycyh;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION";

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @BindView(R.id.btnSilence) Button mBtnSilence;
    @BindView(R.id.btnMaxVolume) Button mBtnMaxVolume;
    @BindView(R.id.seekbarCall) SeekBar mSeekBarCall;
    @BindView(R.id.seekbarRing) SeekBar mSeekBarRing;
    @BindView(R.id.seekbarMedia) SeekBar mSeekBarMusic;
    @BindView(R.id.seekbarClarm) SeekBar mSeekBarAlarm;
    @BindView(R.id.sample_text) TextView mInfo;

    AudioManager mAudioManager;
    int maxCall;
    int maxRing;
    int maxAlarm;
    int maxMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        //registerReceiver(new MyVolumeReceiver(), new IntentFilter(VOLUME_CHANGED_ACTION));
        initViewValue();
        setViewListener();
    }

    private void initViewValue(){
        maxCall = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_VOICE_CALL );
        int current = mAudioManager.getStreamVolume( AudioManager.STREAM_VOICE_CALL );
        mSeekBarCall.setProgress((int)(100.0 * current / maxCall) );

        maxRing = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_RING );
        current = mAudioManager.getStreamVolume( AudioManager.STREAM_RING );
        mSeekBarRing.setProgress((int)(100.0 * current / maxRing) );

        maxAlarm = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_ALARM );
        current = mAudioManager.getStreamVolume( AudioManager.STREAM_ALARM );
        mSeekBarAlarm.setProgress((int)(100.0 * current / maxAlarm) );

        maxMusic = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_MUSIC );
        current = mAudioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
        mSeekBarMusic.setProgress((int)(100.0 * current / maxMusic) );


    }

    private void setViewListener(){
        mBtnSilence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, 0, AudioManager.FLAG_PLAY_SOUND);
                mAudioManager.setStreamVolume(AudioManager.STREAM_RING, 0, AudioManager.FLAG_PLAY_SOUND);
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_PLAY_SOUND);
                mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, 0, AudioManager.FLAG_PLAY_SOUND);
                mSeekBarCall.setProgress(0);
                mSeekBarRing.setProgress(0);
                mSeekBarAlarm.setProgress(0);
                mSeekBarMusic.setProgress(0);
            }
        });
        mBtnMaxVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeekBarCall.setProgress(100);
                mSeekBarRing.setProgress(100);
                mSeekBarAlarm.setProgress(100);
                mSeekBarMusic.setProgress(100);
                mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, maxCall, AudioManager.FLAG_PLAY_SOUND);
                mAudioManager.setStreamVolume(AudioManager.STREAM_RING, maxRing, AudioManager.FLAG_PLAY_SOUND);
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxMusic, AudioManager.FLAG_PLAY_SOUND);
                mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, maxAlarm, AudioManager.FLAG_PLAY_SOUND);
            }
        });
        mSeekBarCall.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, (int)(progress / 100.0 * maxCall), AudioManager.FLAG_PLAY_SOUND);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBarAlarm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, (int)(progress / 100.0 * maxAlarm), AudioManager.FLAG_PLAY_SOUND);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBarMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int)(progress / 100.0 * maxMusic), AudioManager.FLAG_PLAY_SOUND);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBarRing.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_RING, (int)(progress / 100.0 * maxRing), AudioManager.FLAG_PLAY_SOUND);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private class MyVolumeReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(VOLUME_CHANGED_ACTION)){
                initViewValue();
            }
        }
    }

}
