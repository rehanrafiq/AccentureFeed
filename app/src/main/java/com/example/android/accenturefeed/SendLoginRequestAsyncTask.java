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

        // In a POST request, we don't pass the values in the URL.
        //Therefore we use only the web page URL as the parameter of the HttpPost argument
        HttpPost httpPost = new HttpPost("https://accenture-feed.herokuapp.com/api/auth/signin");

        // Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
        //uniquely separate by the other end.
        //To achieve that we use BasicNameValuePair
        //Things we need to pass with the POST request

        BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair("username", paramUsername);
        BasicNameValuePair passwordBasicNameValuePAir = new BasicNameValuePair("password", paramPassword);

        // We add the content that we want to pass with the POST request to as name-value pairs
        //Now we put those sending details to an ArrayList with type safe of NameValuePair
        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
        nameValuePairList.add(usernameBasicNameValuePair);
        nameValuePairList.add(passwordBasicNameValuePAir);

        try {
            // UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs.
            //This is typically useful while sending an HTTP POST request.
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);

            // setEntity() hands the entity (here it is urlEncodedFormEntity) to the request.
            httpPost.setEntity(urlEncodedFormEntity);

            try {
                // HttpResponse is an interface just like HttpPost.
                //Therefore we can't initialize them
                HttpResponse httpResponse = httpClient.execute(httpPost);

                // According to the JAVA API, InputStream constructor do nothing.
                //So we can't initialize InputStream although it is not an interface
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




