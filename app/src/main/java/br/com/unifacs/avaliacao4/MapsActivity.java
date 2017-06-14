package br.com.unifacs.avaliacao4;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
    private Location location;
    private LatLng localizacao;
    private TextView coordenadas;
    private double latitude,longitude;
    SharedPreferences sharedPreferences;
    MarkerOptions marker;

    private Button configurar,fechar;
    private int contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        contador = 0;

        //Localizar Botoes e texto
        coordenadas = (TextView) findViewById(R.id.Coordenadas);
        configurar = (Button) findViewById(R.id.Configurar);
        configurar.setVisibility(View.INVISIBLE);
        fechar = (Button)findViewById(R.id.Fechar);
        fechar.setVisibility(View.INVISIBLE);
        Button menu = (Button) findViewById(R.id.Menu);


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
                    finish();
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
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,3000,1,this);
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location == null){
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);
            }
        }
    }
    //Google Maps Pronto
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        marker = new MarkerOptions();
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2));
        if(sharedPreferences.getBoolean("firstrun",true)) {
        }else{
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-13,-38.5), 16));}
        if(sharedPreferences.getString("maptype","satellite").equals("satellite")){
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);}
        if(sharedPreferences.getString("maptype","normal").equals("normal")){
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);}
        if(sharedPreferences.getBoolean("traffic",true)){
            mMap.setTrafficEnabled(true);
        }else{
            mMap.setTrafficEnabled(false);}
        if(sharedPreferences.getBoolean("indoors",true)){
            mMap.setIndoorEnabled(true);
        }else{
            mMap.setIndoorEnabled(false);}
        if(sharedPreferences.getBoolean("map3d",true)){
            mMap.setBuildingsEnabled(true);
        }else{
            mMap.setBuildingsEnabled(false);}

    }
    //Atualizar localizacao
    public void atualizarMapa(){
        localizacao = new LatLng(latitude, longitude);
        mMap.clear();
        marker.position(localizacao).title("Coordenadas: " +localizacao);
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(localizacao));
        if(sharedPreferences.getString("unitspeed","kmh").equals("kmh")){
            coordenadas.setText("Velocidade em KM/H: " + (location.getSpeed()* 3600));
        }else{
            coordenadas.setText("Velocidade em MPH: " + ((location.getSpeed()* 3600)*0.621371));
        }

    }
    //Mudar lat e long quando localizacao mudar
    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
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

