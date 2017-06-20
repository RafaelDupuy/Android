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
    private Boolean traffic,indoors,map3d,cameraLock;
    private String mapType,unitSpeed,coordinatesFormat;
    private Button trafficOn,trafficOff,
            satellite,normal,
            indoorsButtonOn,indoorsButtonOff,
            map3dButtonOn,map3dButtonOff,
            unitSpeedKm,unitSpeedMph,
            cameraLockButtonOn,cameraLockButtonOff,degreesButton,minutesButton,secondsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configurar);


        //SharedPreferences e configuracoes ativadas
        sharedPreferences = getSharedPreferences("Myprefs",Context.MODE_PRIVATE);

        if(sharedPreferences.getString("maptype","normal").equals("normal")){
            mapType = "normal";
        }else{
            mapType = "satellite";
        }
        if(sharedPreferences.getBoolean("traffic",true)){
            traffic = true;
        }else{
            traffic = false;
        }
        if(sharedPreferences.getBoolean("indoors",true)){
            indoors = true;
        }else{
            indoors = false;
        }
        if(sharedPreferences.getBoolean("map3d",true)){
            map3d = true;
        }else{
            map3d = false;
        }
        if(sharedPreferences.getString("unitspeed","kmh").equals("kmh")){
            unitSpeed = "kmh";
        }else{
            unitSpeed = "mph";
        }
        if(sharedPreferences.getBoolean("cameralock",true)){
            cameraLock = true;
        }else{
            cameraLock = false;
        }
        if(sharedPreferences.getString("coordinatesformat","degrees").equals("degrees")) {
            coordinatesFormat = "degrees";
        }
        if(sharedPreferences.getString("coordinatesformat","minutes").equals("minutes")){
            coordinatesFormat = "minutes";
        }
        if(sharedPreferences.getString("coordinatesformat","seconds").equals("seconds")){
            coordinatesFormat = "seconds";
        }

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
        cameraLockButtonOn = (Button)findViewById(R.id.cameraLockButtonOn);
        cameraLockButtonOff = (Button) findViewById(R.id.cameraLockButtonOff);
        degreesButton = (Button) findViewById(R.id.degreesButton);
        minutesButton = (Button) findViewById(R.id.minutesButton);
        secondsButton = (Button) findViewById(R.id.secondsButton);

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

        //Botao MPH
        unitSpeedMph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unitSpeed = "mph";
                unitSpeedMph.setBackgroundColor(Color.parseColor("#03A9F4"));
                unitSpeedKm.setBackgroundColor(Color.parseColor("#BDBDBD"));
            }
        });

        //Botao Travar Camera
        cameraLockButtonOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraLock = true;
                cameraLockButtonOn.setBackgroundColor(Color.parseColor("#03A9F4"));
                cameraLockButtonOff.setBackgroundColor(Color.parseColor("#BDBDBD"));
            }
        });

        //Botao Destravar Camera
        cameraLockButtonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraLock = false;
                cameraLockButtonOff.setBackgroundColor(Color.parseColor("#03A9F4"));
                cameraLockButtonOn.setBackgroundColor(Color.parseColor("#BDBDBD"));
            }
        });

        //Coordenadas em DEGRAU
        degreesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coordinatesFormat = "degrees";
                degreesButton.setBackgroundColor(Color.parseColor("#03A9F4"));
                minutesButton.setBackgroundColor(Color.parseColor("#BDBDBD"));
                secondsButton.setBackgroundColor(Color.parseColor("#BDBDBD"));
            }
        });

        //Coordenadas em DEGRAU-MINUTO
        minutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coordinatesFormat = "minutes";
                minutesButton.setBackgroundColor(Color.parseColor("#03A9F4"));
                degreesButton.setBackgroundColor(Color.parseColor("#BDBDBD"));
                secondsButton.setBackgroundColor(Color.parseColor("#BDBDBD"));
            }
        });

        //Coordenadas em DEGRAU-MINUTO-SEGUNDO
        secondsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coordinatesFormat = "seconds";
                secondsButton.setBackgroundColor(Color.parseColor("#03A9F4"));
                degreesButton.setBackgroundColor(Color.parseColor("#BDBDBD"));
                minutesButton.setBackgroundColor(Color.parseColor("#BDBDBD"));
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
                    sharedPreferences.edit().putBoolean("map3d", true).apply();
                } else {
                    sharedPreferences.edit().putBoolean("map3d", false).apply();}
                if(unitSpeed.equals("kmh")){
                    sharedPreferences.edit().putString("unitspeed","kmh").apply();
                }else{
                    sharedPreferences.edit().putString("unitspeed","mph").apply();
                }
                if(cameraLock){
                    sharedPreferences.edit().putBoolean("cameralock",true).apply();
                }else{
                    sharedPreferences.edit().putBoolean("cameralock",false).apply();
                }
                if(coordinatesFormat.equals("degrees")){
                    sharedPreferences.edit().putString("coordinatesformat","degrees").apply();
                }
                if(coordinatesFormat.equals("minutes")){
                    sharedPreferences.edit().putString("coordinatesformat","minutes").apply();
                }
                if(coordinatesFormat.equals("seconds")) {
                    sharedPreferences.edit().putString("coordinatesformat","seconds").apply();
                }
                Toast.makeText(getApplicationContext(), "Configuracoes Salvas!", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

        //Botao sair
        Button exitButton = (Button) findViewById(R.id.exitButton);
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
