package com.example.android.accenturefeed;

import android.app.ProgressDialog;
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
import android.widget.TextView;

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
public class ItemActivity extends AppCompatActivity {


    Context context = ItemActivity.this;
    ArrayList<Items> myListItems = new ArrayList<Items>();
    ListView ItemlistView;

    @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent i = getIntent();
        Bundle extras=i.getExtras();
        String id =extras.getString("cat_id");

        class SendGetOneCategoryReqAsyncTask extends AsyncTask<String, Void, String> {

            JSONArray items;
            public ProgressDialog pdialog;


            @Override
            protected String doInBackground(String... params) {
                String id=params[0];


                HttpClient httpClient = new DefaultHttpClient();

                // In a POST request, we don't pass the values in the URL.
                //Therefore we use only the web page URL as the parameter of the HttpPost argument
                HttpGet httpGet = new HttpGet("https://accenture-feed.herokuapp.com/api/items/bycategory/".concat(id));

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
                pdialog = ProgressDialog.show(ItemActivity.this, "Fetching Data", "Please wait...");
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                try {
                    items=new JSONArray(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                    for (int i = 0; i < items.length(); i++) {
                        JSONObject Items = null;
                        try {
                            Items = items.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Items item = new Items();
                        item.setItemTitle(Items.optString("title"));
                        item.setItemId(Items.optString("_id"));
                        item.setItemPubdate(Items.optString("pubDate"));
                        item.setItemLink(Items.optString("link"));
                        item.setItemDesc(Items.optString("description"));
                        myListItems.add(item);
                    }
                pdialog.dismiss();
                if (items.length()==0){
                    TextView textView=(TextView)findViewById(R.id.no_data);
                    textView.setText("there are no items to dislpay");
                }

                ItemlistView = (ListView) findViewById(R.id.listview_items);
                ItemlistView.setAdapter(new ItemsAdapter(context, myListItems));
                ItemlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Items item=(Items)parent.getAdapter().getItem(position);
                        String item_id=item.itemid;
                        String item_title=item.itemtitle;
                        String item_desc=item.itemdesc;
                        String item_pubdate=item.pubdate;
                        String item_link=item.itemlink;
                        Intent item_intent=new Intent(ItemActivity.this,DetailItem.class);
                        item_intent.putExtra("item_id",item_id);
                        item_intent.putExtra("item_title",item_title);
                        item_intent.putExtra("item_desc",item_desc);
                        item_intent.putExtra("item_pubdate",item_pubdate);
                        item_intent.putExtra("item_link",item_link);
                        startActivity(item_intent);
                    }
                });

            }
        }
        SendGetOneCategoryReqAsyncTask asyncTask = (SendGetOneCategoryReqAsyncTask) new SendGetOneCategoryReqAsyncTask();
        asyncTask.execute(id);
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
            Intent productIntent = new Intent(ItemActivity.this,LoginActivity.class);
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

