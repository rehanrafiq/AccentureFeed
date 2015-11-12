package com.example.android.accenturefeed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailitem);
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
            textViewdesc.setText(item_desc);

        }

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
}
