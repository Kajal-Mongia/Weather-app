package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //declare variables
    Button getWeatherButton;
    public static String iconUrl;
    public static TextView weatherSpaceTextView, dateTextView,weatherTagTextView,tempTextView;
    TextView longitudeTextView,latitudeTextView,countryTextView,localityTextView,addressLineTextView;
    public static ImageView weatherIconImage;
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        //assign values
        getWeatherButton=findViewById( R.id.getWeatherButton );
        weatherSpaceTextView=findViewById( R.id.textView );
        longitudeTextView=findViewById( R.id.longitudeTextView );
        latitudeTextView=findViewById( R.id.latituteTextView );
        countryTextView=findViewById( R.id.countryTextView );
        localityTextView=findViewById( R.id.localityTextView );
        addressLineTextView=findViewById( R.id.addressLineTextView );
        dateTextView=findViewById( R.id.dateTextView );
        weatherTagTextView=findViewById( R.id.weatherTagTextView );
        tempTextView=findViewById( R.id.tempTextView );
        weatherIconImage=findViewById( R.id.weatherIcon );

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient( this );


        getWeatherButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ActivityCompat.checkSelfPermission( MainActivity.this
                        , Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
                  && ActivityCompat.checkSelfPermission( MainActivity.this
                        , Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED ){
                    getLocation();
                }else{
                    ActivityCompat.requestPermissions( MainActivity.this
                                    ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }

//                weatherSpaceTextView.setText( "clicked" );
            }
        } );
    }

    private void getLocation() {


        fusedLocationProviderClient.getLastLocation().addOnCompleteListener( new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                Log.d( "DEBUG", "got location bro" );
                if(location!=null){
                    Geocoder geocoder = new Geocoder( MainActivity.this, Locale.getDefault() );
                    List<Address> addresses;
                    try {
                        addresses = geocoder.getFromLocation( location.getLatitude(),location.getLongitude(),1 );
                        Log.d( "DEGUG", "longitude: "+addresses.get( 0 ).getLongitude() );
                        Log.d( "DEGUG", "latitude: "+addresses.get( 0 ).getLatitude() );
                        countryTextView.setText( addresses.get( 0 ).getCountryName() );
                        localityTextView.setText( addresses.get( 0 ).getLocality() );
                        longitudeTextView.setText( String.valueOf( addresses.get( 0 ).getLongitude()) );
                        latitudeTextView.setText( String.valueOf( addresses.get( 0 ).getLatitude() ));
                        addressLineTextView.setText( addresses.get( 0 ).getAddressLine(0) );

                        getWeatherAPI process = new getWeatherAPI(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
                        process.execute();
                        Log.d( "DEBUG2", "HELLO JI" + iconUrl );
                        loadFromUrl( iconUrl );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } );
    }
    public void loadFromUrl(String iconUrl){
        Log.d( "DEBUG", "HAWWWWW"+iconUrl );
        Picasso.with(this).load( iconUrl )
                .into(MainActivity.weatherIconImage, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                } );
    }





}
