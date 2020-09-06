package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    //declare variables
    Button getWeatherButton;
    public static TextView weatherSpaceTextView;
//    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        //assign values
        getWeatherButton=findViewById( R.id.getWeatherButton );
        weatherSpaceTextView=findViewById( R.id.textView );

//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient( this );

        getWeatherButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWeatherAPI process = new getWeatherAPI();
                process.execute();
//                weatherSpaceTextView.setText( "clicked" );
            }
        } );
    }
}
