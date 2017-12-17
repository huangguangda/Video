package cn.edu.gdmec.android.video;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener,SurfaceHolder.Callback{
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Timer time;
    private TimerTask timeTask;
    private boolean isChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getAbsolutePath() + "/beyond.mp4");
            mediaPlayer = new MediaPlayer();
        }catch (IOException e){
            e.printStackTrace();
        }
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        mediaPlayer.setOnPreparedListener(this);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setMax( mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){


            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isChange = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo( seekBar.getProgress());
                isChange = false;
            }
        });
        time = new Timer();
        timeTask = new TimerTask() {
            @Override
            public void run() {
                if(isChange == true){
                    return;
                }
                seekBar.setProgress( mediaPlayer.getCurrentPosition());
            }
        };
        time.schedule( timeTask,0,10);
         }

    public void intent(View view){
        Uri uri = Uri.parse ( "http://android2017.duapp.com/beyond.mp4" );
        Intent intent = new Intent ( Intent.ACTION_VIEW );
        intent.setDataAndType ( uri,"video/mp4" );
        startActivity ( intent );
    }

    public void videoview(View view){
        Uri uri = Uri.parse ( Environment.getExternalStorageDirectory ().getAbsolutePath ()
        + "/beyond.mp4");
        VideoView videoView = (VideoView) findViewById ( R.id.videoview );
        videoView.setMediaController ( new MediaController ( this ) );
        videoView.setVideoURI ( uri );
        videoView.start ();
        videoView.requestFocus ();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mediaPlayer.setDisplay( surfaceHolder );
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
