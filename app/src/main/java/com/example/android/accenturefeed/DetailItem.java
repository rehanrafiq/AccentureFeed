package com.example.android.accenturefeed;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailItem extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailitem);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Intent i=getIntent();
        Bundle extras=i.getExtras();
        String item_id=extras.getString("item_id");
        String item_title=extras.getString("item_title");
        String item_desc=extras.getString("item_desc");
        String item_pubdate=extras.getString("item_pubdate");
        String item_link=extras.getString("item_link");




        if (extras!=null){
            TextView textViewTitle=(TextView)findViewById(R.id.detail_item_title);
            textViewTitle.setText(item_title);
            TextView textViewdate=(TextView)findViewById(R.id.detail_item_date);
            textViewdate.setText(item_pubdate);
            TextView textViewdesc=(TextView)findViewById(R.id.detail_item_desc);
            textViewdesc.setText(desc(item_desc));

        }

//        Intent cat_intent= new Intent(DetailItem.this, ItemActivity.class);
//        startActivity(cat_intent);
//        Intent i = getIntent();
//        Bundle extras=i.getExtras();
//        String id =extras.getString("cat_item_id");
//
//        if (extras!=null){
//
//            TextView textView=(TextView)findViewById(R.id.detail_item_text);
//            textView.setText(id);
//        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_logout) {
            Intent productIntent = new Intent(DetailItem.this,LoginActivity.class);
            startActivity(productIntent);
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public String desc(String description) {
        return Html.fromHtml(description).toString();
    }
}

