package br.com.unifacs.avaliacao4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;



public class Creditos extends Activity {
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creditos);

        //Animacao e texto
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.creditos);
        TextView creditos = (TextView)findViewById(R.id.creditos);
        creditos.setTextColor(Color.parseColor("#3F51B5"));
        creditos.setText("Creditos \n\n"+ "Rafael Ferreira Dupuy de Lome");
        creditos.startAnimation(animation);

        //Audio
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,10,0);
        mediaPlayer = MediaPlayer.create(this,R.raw.gbcasino);
        mediaPlayer.start();

        //Fechar Clicando na tela
        creditos.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View arg0,MotionEvent arg1){
                Intent myIntent = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(myIntent);
                finish();
                return true;
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }
}

