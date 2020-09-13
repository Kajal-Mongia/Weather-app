package com.example.weather;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class getWeatherAPI extends AsyncTask<Void,Void,Void> {
    String data="";
    String singleParsed="";
    String date;
    String weatherTag;
    String temp;
    String iconUrl2;

    double lat,longt;
    getWeatherAPI(double latitude,double longitude){
        lat=latitude;
        longt=longitude;
    }
   @Override
    protected Void doInBackground(Void... voids) {
       try {
           String urlString ="https://www.metaweather.com/api/location/search/?lattlong="+lat+","+longt;
           URL urlLatLong = new URL( urlString );
           HttpsURLConnection httpsURLConnection = (HttpsURLConnection) urlLatLong.openConnection();
           InputStream inputStream = httpsURLConnection.getInputStream();
           BufferedReader br = new BufferedReader( new InputStreamReader( inputStream ) );
           String line = " ";
           while (line != null) {
               line = br.readLine();
               data += line;
           }

           JSONArray jsonArray=new JSONArray( data );
           JSONObject jsonObject = (JSONObject) jsonArray.get(0);
           singleParsed = "\nTitle : "+jsonObject.get( "title" )+"\nLocation Type:"+jsonObject.get( "location_type" )
                       +"\nWhere on earth id:"+jsonObject.get("woeid")+"\nlatt_long:"+jsonObject.get( "latt_long" );

           String urlStr2 = "https://www.metaweather.com/api/location/"+jsonObject.get("woeid")+"/";
           URL urlWoeid = new URL( urlStr2 );
           httpsURLConnection=(HttpsURLConnection)urlWoeid.openConnection();
           inputStream=httpsURLConnection.getInputStream();
           br = new BufferedReader( new InputStreamReader( inputStream ) );
           line = " ";
           data="";
           while (line != null) {
               line = br.readLine();
               data += line;
           }
           Log.d( "Weather DATA : ", "" + data);
           JSONObject jsonObject1 = new JSONObject( data );
           JSONArray jsonArray1 = new JSONArray( ""+jsonObject1.get("consolidated_weather") );
           JSONObject weatherTodayObject = (JSONObject)jsonArray1.get(0);

           date=String.valueOf( weatherTodayObject.get("applicable_date"));
           weatherTag=String.valueOf( weatherTodayObject.get( "weather_state_name" ));
           temp= String.valueOf( weatherTodayObject.get( "the_temp" ) );
           String icon_abbr =weatherTodayObject.getString( "weather_state_abbr" );
           iconUrl2 = "https://www.metaweather.com/api/static/img/weather/png/64/"+icon_abbr+".png";
       } catch (IOException | JSONException e) {
           e.printStackTrace();
       }

       return null;
   }

    @Override
    protected void onPostExecute(Void aVoid) {

       MainActivity.weatherSpaceTextView.setText( singleParsed );
       MainActivity.weatherTagTextView.setText( weatherTag );
       MainActivity.dateTextView.setText( date );
       MainActivity.tempTextView.setText( temp );
        MainActivity.iconUrl=iconUrl2;
//        Log.d( "DEBUG", "HELLO JI "+iconUrl2 );
        super.onPostExecute( aVoid );
    }


}
