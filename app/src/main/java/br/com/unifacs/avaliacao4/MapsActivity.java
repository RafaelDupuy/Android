package br.com.unifacs.avaliacao4;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private TextView coordenadas,velocidade;
    private double latitude,longitude;
    SharedPreferences sharedPreferences;
    MarkerOptions marker;
    private float speed;
    private Button configurar,fechar;
    private int contador;
    private String strLat,strLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        contador = 0;

        //Localizar Botoes e texto
        velocidade = (TextView) findViewById(R.id.Velocidade);
        velocidade.setVisibility(View.INVISIBLE);
        coordenadas = (TextView) findViewById(R.id.Coordenadas);
        coordenadas.setVisibility(View.INVISIBLE);
        configurar = (Button) findViewById(R.id.Configurar);
        configurar.setVisibility(View.INVISIBLE);
        fechar = (Button)findViewById(R.id.Fechar);
        fechar.setVisibility(View.INVISIBLE);
        Button menu = (Button) findViewById(R.id.Menu);

        //Configuracao Landscape
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            velocidade.setVisibility(View.VISIBLE);
            coordenadas.setVisibility(View.VISIBLE);
        }

        //Carregar SharedPreferences e configuracao pela primeira vez
        sharedPreferences = getSharedPreferences("Myprefs",Context.MODE_PRIVATE);

        if (sharedPreferences.getBoolean("firstrun", true)) {
            Toast.makeText(getApplicationContext(), "Bem vindo! Configure o app pela primeira vez!", Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(getApplicationContext(),Configurar.class);
            startActivity(myIntent);
            sharedPreferences.edit().putBoolean("firstrun",false).apply();
            sharedPreferences.edit().putString("maptype","normal").apply();
            sharedPreferences.edit().putBoolean("traffic",false).apply();
            sharedPreferences.edit().putBoolean("indoors",false).apply();
            sharedPreferences.edit().putBoolean("map3d",false).apply();
            sharedPreferences.edit().putString("unitspeed","kmh").apply();
            sharedPreferences.edit().putString("coordinatesformat","seconds").apply();
        }

        //Botao Menu
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(configurar.getVisibility()== View.INVISIBLE) {
                    configurar.setVisibility(View.VISIBLE);
                    fechar.setVisibility(View.VISIBLE);
                }else{
                    configurar.setVisibility(View.INVISIBLE);
                    fechar.setVisibility(View.INVISIBLE);
                }
                contador++;
                if(contador == 10) {
                    Intent myIntent = new Intent(getApplicationContext(),Creditos.class);
                    startActivity(myIntent);
                }
            }
        });

        //Botao Configurar
        configurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(),Configurar.class);
                startActivity(myIntent);
                finish();
            }
        });

        //Botao Fechar
        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAndRemoveTask();
            }
        });

        //Pegar Localizacao + checar permissao localizacao
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Sem permissoes", Toast.LENGTH_SHORT).show();
        } else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,1,this);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location == null){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
            }
        }
    }
    //Google Maps Pronto + configuracoes
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        marker = new MarkerOptions();
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2));
        if (sharedPreferences.getString("maptype", "satellite").equals("satellite")) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);}
        if (sharedPreferences.getString("maptype", "normal").equals("normal")) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);}
        if (sharedPreferences.getBoolean("traffic", true)) {
            mMap.setTrafficEnabled(true);
        } else {
            mMap.setTrafficEnabled(false);}
        if (sharedPreferences.getBoolean("indoors", true)) {
            mMap.setIndoorEnabled(true);
        } else {
            mMap.setIndoorEnabled(false);}
        if (sharedPreferences.getBoolean("map3d", true)) {
            mMap.setBuildingsEnabled(true);
        } else {
            mMap.setBuildingsEnabled(false);}
        if (sharedPreferences.getBoolean("cameralock", true)) {
            mMap.getUiSettings().setRotateGesturesEnabled(false);
        } else {
            mMap.getUiSettings().setRotateGesturesEnabled(true);}
    }
    //Atualizar localizacao + unidade de velocidade
    public void atualizarMapa(){
        LatLng localizacao = new LatLng(latitude, longitude);
        mMap.clear();
        marker.position(localizacao);
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(localizacao));
        if (sharedPreferences.getString("unitspeed", "kmh").equals("kmh")) {
            velocidade.setText("Velocidade em KM/H: " + (speed * 3.6));
        } else {
            velocidade.setText("Velocidade em MPH: " + ((speed) * 3.6) * 0.621371);
        }
        coordenadas.setText("Latitude : " + strLat + "|" + "Longitude: " + strLng);

    }

    //Informacoes GPS quando localizacao mudar + formato da latitude | longitude
    @Override
    public void onLocationChanged(Location location) {
        speed = location.getSpeed();
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        if(sharedPreferences.getString("coordinatesformat","degrees").equals("degrees")){
            strLat = location.convert(latitude,location.FORMAT_DEGREES);
            strLng = location.convert(longitude,location.FORMAT_DEGREES);
        }
        if(sharedPreferences.getString("coordinatesformat","minutes").equals("minutes")){
            strLat = location.convert(latitude,location.FORMAT_MINUTES);
            strLng = location.convert(longitude,location.FORMAT_MINUTES);
        }
        if(sharedPreferences.getString("coordinatesformat","seconds").equals("seconds")){
            strLat = location.convert(latitude,location.FORMAT_SECONDS);
            strLng = location.convert(longitude,location.FORMAT_SECONDS);
        }
        atualizarMapa();
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
    @Override
    public void onProviderEnabled(String provider) {
    }
    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    //Remover GPS quando fechar app
    protected void onDestroy() {
        super.onDestroy();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.removeUpdates(this);
        }
    }
    //Remove GPS quando minimizar app (Pausar GPS)
    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.removeUpdates(this);
        }
    }
}

