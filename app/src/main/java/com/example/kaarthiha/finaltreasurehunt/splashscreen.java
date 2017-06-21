package com.example.kaarthiha.finaltreasurehunt;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.digits.sdk.android.Digits;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import io.fabric.sdk.android.Fabric;

public class splashscreen extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "LHQB5fgrXFNngscFj5MmoJ8VL";
    private static final String TWITTER_SECRET = "DiPeKiEk6VyhcLywhLDXHVgnlSNbrZNUIw9tIZLk0HytJYOIY3";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());
        setContentView(R.layout.activity_splashscreen);
        MediaPlayer mp = MediaPlayer.create(splashscreen.this,R.raw.bgsong);
        mp.start();
        Thread th = new Thread(){
            @Override
            public void run() {
                try {

                    sleep(2000);

                    Intent i = new Intent(getApplicationContext(),introactivity.class);
                    startActivity(i);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        th.start();
    }
}
