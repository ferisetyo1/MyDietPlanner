package feri.com.mydietplanner.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import feri.com.mydietplanner.R;


public class Splash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGHT = 6000;
    private ImageView gif;
    @Override
    protected void onCreate(Bundle incicle){
        super.onCreate(incicle);
        setContentView(R.layout.splashku);
        gif = findViewById(R.id.cobagif);
        Glide.with(this).asGif()
                .load(R.drawable.splashfix).into(gif);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = null;
                mainIntent = new Intent(Splash.this,MainActivity.class);

                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        },SPLASH_DISPLAY_LENGHT);

    }

}