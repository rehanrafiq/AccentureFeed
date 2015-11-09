package com.example.android.accenturefeed;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@SuppressWarnings("ALL")
public class SendPostCategoriesReqAsyncTask extends AsyncTask<Void, Void, String> {

    JSONArray categories;

    @Override
    protected String doInBackground(Void... params) {

        HttpClient httpClient = new DefaultHttpClient();

        // In a POST request, we don't pass the values in the URL.
        //Therefore we use only the web page URL as the parameter of the HttpPost argument
        HttpGet httpGet = new HttpGet("https://accenture-feed.herokuapp.com/api/categories");

        try {
            // HttpResponse is an interface just like HttpPost.
            //Therefore we can't initialize them
            HttpResponse httpResponse = httpClient.execute(httpGet);

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
        return null;
    }

    public interface AsyncResponse {

        void processFinish(JSONArray categories);
    }

    public AsyncResponse delegate=null ;

    public SendPostCategoriesReqAsyncTask(AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);
        try {
            categories = new JSONArray(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Construct the data source

//                        ArrayList list = Category.fromJson(categories);
//                        ListView listView = (ListView) rootView.findViewById(R.id.listview_categories);
//                        listView.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();





//        final ArrayList data = new ArrayList<>();
//
//
//        for (int i = 0; i < categories.length(); i++) {
//            JSONObject category = null;
//            try {
//                category = categories.getJSONObject(i);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            try {
//                if (category != null) {
////                                    Category cat = new Category(category.getString("title"),category.getString("_id"));
//                    data.add(category.getString("title"));
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
        delegate.processFinish(categories);
    }

}