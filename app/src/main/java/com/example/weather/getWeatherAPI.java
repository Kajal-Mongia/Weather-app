package com.example.weather;

import android.os.AsyncTask;

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
    String dataParsed="";
   @Override
    protected Void doInBackground(Void... voids) {
       try {
           URL url = new URL( "https://www.metaweather.com/api/location/search/?query=san" );
           HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
           InputStream inputStream = httpsURLConnection.getInputStream();
           BufferedReader br = new BufferedReader( new InputStreamReader( inputStream ) );
           String line = " ";
           while (line != null) {
               line = br.readLine();
               data += line;
           }

           JSONArray jsonArray=new JSONArray( data );
           for(int i=0;i<jsonArray.length();i++){
               JSONObject jsonObject = (JSONObject) jsonArray.get(i);
               singleParsed = "\nTitle : "+jsonObject.get( "title" )+"\nLocation Type:"+jsonObject.get( "location_type" )
                       +"\nWhere on earth id:"+jsonObject.get("woeid")+"\nlatt_long:"+jsonObject.get( "latt_long" );
                dataParsed+=singleParsed+"\n";
           }

       } catch (IOException | JSONException e) {
           e.printStackTrace();
       }

       return null;
   }

    @Override
    protected void onPostExecute(Void aVoid) {

       MainActivity.weatherSpaceTextView.setText( dataParsed );
        super.onPostExecute( aVoid );
    }
}
