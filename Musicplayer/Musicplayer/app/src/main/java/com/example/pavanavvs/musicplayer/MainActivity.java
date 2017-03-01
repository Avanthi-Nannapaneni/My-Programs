package com.example.pavanavvs.musicplayer;

import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {


    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creating a media player for Application level of context
         mp=MediaPlayer.create(getApplicationContext(),R.raw.m);

// Button creation for music player to start
        Button start=(Button)findViewById(R.id.button);
// Button creation for music player to stop
        Button stop=(Button)findViewById(R.id.button2);

// onclick listener for button start view
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mp.start();

            }
        });

// onclick listener for button stop view
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
mp.stop(); // Thread kills
            }
        });
    }


}
