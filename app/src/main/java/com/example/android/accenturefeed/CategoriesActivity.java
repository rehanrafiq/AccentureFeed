package com.example.android.accenturefeed;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


@SuppressWarnings("ALL")
    public class CategoriesActivity extends AppCompatActivity {

    Context context = CategoriesActivity.this;
    ArrayList<Category> myList = new ArrayList<Category>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i=getIntent();
        Bundle extras=i.getExtras();
        if (extras != null) {
            String name=extras.getString("username");
            Toast.makeText(getApplicationContext(), "Welcome " + name, Toast.LENGTH_SHORT).show();
        }

        class SendPostCategoriesReqAsyncTask extends AsyncTask<Void, Void, String> {
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

            @Override
            protected void onPreExecute() {

//                new Thread() {
//                    public void run() {
//                        try{
//                            // just doing some long operation
//                            sleep(3000);
//                        } catch (Exception e) {  }
//                        pDialog.dismiss();
//                    }
//                }.start();

            }

//            public interface AsyncResponse {
//
//                void processFinish(ArrayList<Category> mylist);
//            }
//
//            public AsyncResponse delegate=null ;
//
//            public SendPostCategoriesReqAsyncTask(AsyncResponse delegate){
//                this.delegate = delegate;
//            }



            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);
                try {
                    categories = new JSONArray(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < categories.length(); i++) {
                    //      // Create a new object for each list item
                    JSONObject category = null;
                    try {
                        category = categories.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Category ld = new Category();
                    ld.setTitle(category.optString("title"));
                    ld.setId(category.optString("_id"));
                    //          ld.setImgResId(img[i]);
                    // Add this object into the ArrayList myListItems
                    myList.add(ld);


                }



                listView = (ListView) findViewById(R.id.listview_categories);
                listView.setAdapter(new CategoriesAdapter(context, myList));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Category cat = (Category) parent.getAdapter().getItem(position);
                        String cat_id = cat.id;
                        Intent cat_intent= new Intent(CategoriesActivity.this, ItemActivity.class);
                        cat_intent.putExtra("cat_id", cat_id);
                        startActivity(cat_intent);
                    }
                });


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
//  //                                  Category cat = new Category(category.getString("title"),category.getString("_id"));
//                    data.add(category.getString("title"));
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//                delegate.processFinish(myListItems);
            }
        }
        SendPostCategoriesReqAsyncTask asyncTask = (SendPostCategoriesReqAsyncTask) new SendPostCategoriesReqAsyncTask();
        asyncTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}