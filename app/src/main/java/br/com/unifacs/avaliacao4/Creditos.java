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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;



public class Creditos extends Activity {
    MediaPlayer mediaPlayer;
    private RatingBar ratingBar;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creditos);


        //Encontrar barra
        ratingBar = (RatingBar)findViewById(R.id.ratingBar2);

        //Animacao e texto
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.creditos);
        TextView creditos = (TextView)findViewById(R.id.creditos);
        creditos.setTextColor(Color.parseColor("#3F51B5"));
        creditos.setText("Creditos \n\n"+ "Rafael Ferreira Dupuy de Lome" +"\n"+"NOME 2 \n"+"NOME 3 \n"+"Nome 4 \n"+"Nome 5 \n"+"Nome 6 \n");
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
                if(ratingBar.getRating() == 5){
                    Toast.makeText(getApplicationContext(),"Teste",Toast.LENGTH_SHORT).show();
                }
                ratingBar.setNumStars(0);
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

