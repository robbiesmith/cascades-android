package com.snowcascades.app;
//package de.vogella.android.twitter.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

public class ParseJSON extends Activity {
  
	// unused? 
/** Called when the activity is first created. */

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    // Just for testing, allow network access in the main thread
    // NEVER use this in productive code
    StrictMode.ThreadPolicy policy = new StrictMode.
    ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy); 
    
    
//    setContentView(R.layout.main);
    String readTwitterFeed = readTwitterFeed();
//    try {
//      JSONArray jsonArray = new JSONArray(readTwitterFeed);
//      Log.i(ParseJSON.class.getName(),
//          "Number of entries " + jsonArray.length());
//      for (int i = 0; i < jsonArray.length(); i++) {
//        JSONObject jsonObject = jsonArray.getJSONObject(i);
//        Log.i(ParseJSON.class.getName(), jsonObject.getString("text"));
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
  }

  static public String readTwitterFeed() {
    StringBuilder builder = new StringBuilder();
    HttpClient client = new DefaultHttpClient();
    HttpGet httpGet = new HttpGet("http://snowcascades.com/cascade/data.json");
    try {
      HttpResponse response = client.execute(httpGet);
      StatusLine statusLine = response.getStatusLine();
      int statusCode = statusLine.getStatusCode();
      if (statusCode == 200) {
        HttpEntity entity = response.getEntity();
        InputStream content = entity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
        String line;
        while ((line = reader.readLine()) != null) {
          builder.append(line);
        }
      } else {
        Log.e(ParseJSON.class.toString(), "Failed to download file");
      }
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return builder.toString();
  }
} 
