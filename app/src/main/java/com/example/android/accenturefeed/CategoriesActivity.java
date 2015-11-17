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
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

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
                HttpGet httpGet = new HttpGet("https://accenture-feed.herokuapp.com/api/categories");

                try {
                    HttpResponse httpResponse = httpClient.execute(httpGet);

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
            protected void onPostExecute(String result) {

                super.onPostExecute(result);
                try {
                    categories = new JSONArray(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < categories.length(); i++) {
                    JSONObject category = null;
                    try {
                        category = categories.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Category ld = new Category();
                    ld.setTitle(category.optString("title"));
                    ld.setId(category.optString("_id"));
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

            }
        }
        SendPostCategoriesReqAsyncTask asyncTask = (SendPostCategoriesReqAsyncTask) new SendPostCategoriesReqAsyncTask();
        asyncTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            Intent productIntent = new Intent(CategoriesActivity.this,LoginActivity.class);
            startActivity(productIntent);
            return true;
        }

        switch (item.getItemId()) {

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}