package com.example.android.accenturefeed;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

class SendLoginRequestAsyncTask extends AsyncTask<String, Void, String> {



    @SuppressWarnings("deprecation")
    @Override
    protected String doInBackground(String... params) {
        String paramUsername = params[0];
        String paramPassword = params[1];

        HttpClient httpClient = new DefaultHttpClient();

        HttpPost httpPost = new HttpPost("https://accenture-feed.herokuapp.com/api/auth/signin");

        BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair("username", paramUsername);
        BasicNameValuePair passwordBasicNameValuePAir = new BasicNameValuePair("password", paramPassword);

        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
        nameValuePairList.add(usernameBasicNameValuePair);
        nameValuePairList.add(passwordBasicNameValuePAir);

        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);

            httpPost.setEntity(urlEncodedFormEntity);

            try {
                HttpResponse httpResponse = httpClient.execute(httpPost);

                InputStream inputStream = httpResponse.getEntity().getContent();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder stringBuilder = new StringBuilder();

                String bufferedStrChunk = null;

                while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                    stringBuilder.append(bufferedStrChunk);
                }
                return stringBuilder.toString();

            } catch (ClientProtocolException cpe) {
                cpe.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }

        return null;
    }

    public interface AsyncResponse {
        void processFinish(String output);
    }

    public AsyncResponse delegate = null;

    public SendLoginRequestAsyncTask(AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        JSONObject myJson = null;
        try {
            myJson = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String name = myJson.optString("displayName");
        delegate.processFinish(name);


    }
}




