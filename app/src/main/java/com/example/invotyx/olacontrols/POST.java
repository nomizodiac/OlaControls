package com.example.invotyx.olacontrols;

import android.os.Bundle;
import android.util.Log;

public class POST implements AsyncResponse {

    private String url="";
    private String body= "";
    private String result="";
    private Boolean isProcessExecuted = false;
    public POST(String url, String body)
    {
        this.url=url;
        this.body=body;
        this.execute();


    }
    public void execute()
    {
        HttpPostRequest postReq =new HttpPostRequest(url,body);
        postReq.delegate = this;
        postReq.execute();
    }

    //this override the implemented method from asyncTask
    @Override
    public void processFinish(String result){
        this.result=result;
        isProcessExecuted = true;

    }

    public String getResult() {
        do{

        }
        while(isProcessExecuted);
        return result;
    }
}
