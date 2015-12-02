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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

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
    public class CategoriesActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

//    public class CategoriesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    Context context = CategoriesActivity.this;
    ArrayList<Category> myList = new ArrayList<Category>();
    ListView listView;
    private GoogleApiClient mGoogleApiClient;
//    ListView listView1;
//    private String[] allcategories;
//    private ActionBarDrawerToggle drawerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_activity);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


//
//        listView1=(ListView)findViewById(R.id.drawer_list);
//        allcategories=getResources().getStringArray(R.array.all_activities);
//        ArrayAdapter<String> itemsAdapter =
//                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allcategories);
//        listView1.setAdapter(itemsAdapter);
//        listView1.setOnItemClickListener(this);
//
//        DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
//        drawerListener=new ActionBarDrawerToggle(this,drawerLayout,R.drawable.ic_drawer,R.string.drawer_open+R.string.drawer_close){
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                Toast.makeText(CategoriesActivity.this,"Drawer Opened",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                Toast.makeText(CategoriesActivity.this,"Drawer Closed",Toast.LENGTH_SHORT).show();
//            }
//        };
//        drawerLayout.setDrawerListener(drawerListener);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
//
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        drawerListener.syncState();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        if (drawerListener.onOptionsItemSelected(item)){
//            return true;
//        }
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    Intent logoutIntent = new Intent(CategoriesActivity.this,LoginActivity.class);
                    startActivity(logoutIntent);
                }
            });

            Intent logoutIntent = new Intent(CategoriesActivity.this,LoginActivity.class);
            startActivity(logoutIntent);
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

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
//        @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        drawerListener.onConfigurationChanged(newConfig);
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//        Toast.makeText(this,allcategories[position]+"was selected",Toast.LENGTH_SHORT).show();
//        selectItem(position);
//
//    }
//
//    public void selectItem(int position) {
//        listView1.setItemChecked(position, true);
//        setTitle(allcategories[position]);
//    }
//    public void setTitle(String title){
//        getSupportActionBar().setTitle(title);
//    }

}