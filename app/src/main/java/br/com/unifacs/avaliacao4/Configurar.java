package br.com.unifacs.avaliacao4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Configurar extends Activity {
    SharedPreferences sharedPreferences;
    private Boolean traffic,indoors,map3d;
    private String mapType,unitSpeed;
    private Color On;
    private Button trafficOn,trafficOff,satellite,normal,indoorsButtonOn,indoorsButtonOff,exitButton,
    map3dButtonOn,map3dButtonOff,unitSpeedKm,unitSpeedMph;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configurar);
        sharedPreferences = getSharedPreferences("Myprefs", Context.MODE_PRIVATE);

        //Encontrar Botoes
        satellite = (Button) findViewById(R.id.mapButtonSatellite);
        normal = (Button) findViewById(R.id.mapButtonNormal);
        trafficOn = (Button) findViewById(R.id.trafficButtonOn);
        trafficOff = (Button) findViewById(R.id.trafficButtonOff);
        indoorsButtonOn = (Button) findViewById(R.id.indoorsButtonOn);
        indoorsButtonOff = (Button) findViewById(R.id.indoorsButtonOff);
        map3dButtonOn = (Button) findViewById(R.id.map3dButtonOn);
        map3dButtonOff = (Button) findViewById(R.id.map3dButtonOff);
        unitSpeedKm = (Button) findViewById(R.id.unitSpeedKm);
        unitSpeedMph = (Button) findViewById(R.id.unitSpeedMph);

        //Botao Satelite
        satellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapType = "satellite";
                satellite.setBackgroundColor(Color.parseColor("#03A9F4"));
                normal.setBackgroundColor(Color.parseColor("#BDBDBD"));
                map3d = false;
                map3dButtonOff.setBackgroundColor(Color.parseColor("#03A9F4"));
                map3dButtonOn.setBackgroundColor(Color.parseColor("#BDBDBD"));
            }
        });

        //Botao normal
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapType = "normal";
                normal.setBackgroundColor(Color.parseColor("#03A9F4"));
                satellite.setBackgroundColor(Color.parseColor("#BDBDBD"));

            }
        });

        //Botao Trafego Ativado
        trafficOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                traffic = true;
                trafficOn.setBackgroundColor(Color.parseColor("#03A9F4"));
                trafficOff.setBackgroundColor(Color.parseColor("#BDBDBD"));
            }
        });
        //Botao Trafego Desativado
        trafficOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                traffic = false;
                trafficOff.setBackgroundColor(Color.parseColor("#03A9F4"));
                trafficOn.setBackgroundColor(Color.parseColor("#BDBDBD"));
            }
        });

        //Botao Mapas internos Ativado
        indoorsButtonOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indoors = true;
                indoorsButtonOn.setBackgroundColor(Color.parseColor("#03A9F4"));
                indoorsButtonOff.setBackgroundColor(Color.parseColor("#BDBDBD"));
            }
        });
        //Botao Mapas internos Desativado
        indoorsButtonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indoors = false;
                indoorsButtonOff.setBackgroundColor(Color.parseColor("#03A9F4"));
                indoorsButtonOn.setBackgroundColor(Color.parseColor("#BDBDBD"));
            }
        });

        //Botao Construcoes 3D Ativado
        map3dButtonOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map3d = true;
                map3dButtonOn.setBackgroundColor(Color.parseColor("#03A9F4"));
                map3dButtonOff.setBackgroundColor(Color.parseColor("#BDBDBD"));
            }
        });

        //Botao Construcoes 3D Desativado
        map3dButtonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map3d = false;
                map3dButtonOff.setBackgroundColor(Color.parseColor("#03A9F4"));
                map3dButtonOn.setBackgroundColor(Color.parseColor("#BDBDBD"));
            }
        });

        //Botao KM/H
        unitSpeedKm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unitSpeed = "kmh";
                unitSpeedKm.setBackgroundColor(Color.parseColor("#03A9F4"));
                unitSpeedMph.setBackgroundColor(Color.parseColor("#BDBDBD"));
            }
        });
        unitSpeedMph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unitSpeed = "mph";
                unitSpeedMph.setBackgroundColor(Color.parseColor("#03A9F4"));
                unitSpeedKm.setBackgroundColor(Color.parseColor("#BDBDBD"));
            }
        });

        //Botao Salvar alteracoes
        Button saveConfig = (Button) findViewById(R.id.saveButton);
        saveConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (traffic) {
                    sharedPreferences.edit().putBoolean("traffic", true).apply();
                } else {
                    sharedPreferences.edit().putBoolean("traffic", false).apply();}
                if (mapType.equals("satellite")) {
                    sharedPreferences.edit().putString("maptype", "satellite").apply();
                } else {
                    sharedPreferences.edit().putString("maptype", "normal").apply();}
                if (indoors) {
                    sharedPreferences.edit().putBoolean("indoors", true).apply();
                } else {
                    sharedPreferences.edit().putBoolean("indoors", false).apply();}
                if (map3d) {
                    sharedPreferences.edit().putBoolean("3dmap", true).apply();
                } else {
                    sharedPreferences.edit().putBoolean("3dmap", false).apply();}
                if(unitSpeed.equals("kmh")){
                    sharedPreferences.edit().putString("unitspeed","kmh").apply();
                }else{
                    sharedPreferences.edit().putString("unitspeed","mph").apply();
                }
                Toast.makeText(getApplicationContext(), "Configuracoes Salvas!", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(myIntent);
                finish();
            }
        });
        //Botao sair
        exitButton = (Button) findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(myIntent);
                finish();
            }
        });
    }
}
