package com.example.invotyx.olacontrols;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetRequest extends AsyncTask<String , Void, String> {
    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    public String REQUEST_URL = null;


    public HttpGetRequest(String url) {
        this.REQUEST_URL = url;
    }

    @Override
    protected String doInBackground(String... params){
        String stringUrl = REQUEST_URL;
        String result = null;
        String inputLine;
        try {
            //Create a URL object holding our url
            URL myUrl = new URL(stringUrl);
            //Create a connection
            HttpURLConnection connection =(HttpURLConnection)
                    myUrl.openConnection();
            //Set methods and timeouts
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.addRequestProperty("Authorization", "Basic " + "YWRtaW46ODg4ODg4");
            //Connect to our url
            connection.connect();
            //Create a new InputStreamReader
            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream());
            //Create a new buffered reader and String Builder
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            //Check if the line we are reading is not null
            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }
            //Close our InputStream and Buffered reader
            reader.close();
            streamReader.close();
            //  Log.i("Type of Response:",""+stringBuilder.getClass().getName());
            //Set our result equal to our stringBuilder
            result = stringBuilder.toString();
           /* JSONArray jsonArr = new JSONArray(result);
            Log.i("Result of Get Request",":"+jsonArr.length());
            for (int i = 0; i < jsonArr.length(); i++)
            {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                Log.i("",""+jsonObj.toString(4));
            }*/

        }
        catch(IOException e){
            e.printStackTrace();
            result = null;
        }/* catch (JSONException e) {
                e.printStackTrace();
            }*//* catch (JSONException e) {
            e.printStackTrace();
        }*/
        return result;
    }
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }
}