package com.example.drivingtheoryapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;




/*

Special thanks to Vishnu Sivadas for providing this library

Making HttpURLConnection easy and secure. Best method to implement httpurlconnection in android.

        www.vishnusivadas.com*/


public class FetchData extends Thread {
    public String url;
    String data = "Empty";

    public FetchData(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(this.url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder result = new StringBuilder();
            String result_line;
            while ((result_line = bufferedReader.readLine()) != null) {
                result.append(result_line);
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            setData(result.toString());
        } catch (IOException e) {
            setData(e.toString());
        }
    }

    public boolean startFetch() {
        FetchData.this.start();
        return true;
    }

    public boolean onComplete() {
        while (true) {
            if (!this.isAlive()) {
                return true;
            }
        }
    }

    public String getResult() {
        return this.getData();
    }

    public void setData(String data) {
        this.data = data;
    }


    public String getData() {
        return data;
    }
}
