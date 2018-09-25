package com.example.invotyx.olacontrols;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HttpPostRequest extends AsyncTask<String , Void, String> {
    public static final String REQUEST_METHOD = "POST";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    public String REQUEST_URL = null;
    public String REQUEST_BODY = null;
    public String final_result=null;
    public AsyncResponse delegate = null;

    public HttpPostRequest(String url, String body) {
        this.REQUEST_URL = url;
        this.REQUEST_BODY = body;
    }



    @Override
    protected String doInBackground(String... params){
        String stringUrl = REQUEST_URL;
        String requestBody = REQUEST_BODY;
        String result = null;
        String inputLine;
        try {
            //Create a URL object holding our url
            URL myUrl = new URL(stringUrl);
            requestBody = URLEncoder.encode(requestBody, "UTF-8");
            //Create a connection
            HttpURLConnection connection =(HttpURLConnection)
                    myUrl.openConnection();
            //Set methods and timeouts
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; UTF-8");
            connection.addRequestProperty("Authorization", "Basic " + "YWRtaW46ODg4ODg4");
            connection.setRequestProperty("Accept", "application/x-www-form-urlencoded; UTF-8");
            connection.setDoOutput(true);
            connection.setDoInput(true);


            //Send the post body
            if (requestBody != null) {
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(requestBody);
                writer.flush();
            }
            /*
             */


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
            //Set our result equal to our stringBuilder
            result = stringBuilder.toString();
            Log.i("Result of Post Request","="+result);
        }
        catch(IOException e){
            e.printStackTrace();
            result = null;
        }/* catch (JSONException e) {
                e.printStackTrace();
            }*/
        this.final_result=result;
        return result;
    }
    protected void onPostExecute(String result){
        delegate.processFinish(result);
       // super.onPostExecute(result);
    }




}