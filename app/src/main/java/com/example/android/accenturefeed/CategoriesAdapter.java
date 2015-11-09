package com.example.android.accenturefeed;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;


public class CategoriesAdapter extends ArrayAdapter<Category>{

    Context context;
    JSONArray data;
    AppCompatActivity parentActivity;

    public CategoriesAdapter(Context context, int resource, JSONArray objects,AppCompatActivity activity) {

        super(context, resource, (List<Category>) objects);
        this.data = objects;
        this.parentActivity = activity;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CellHolder holder = null;
        if (convertView == null) {
            holder = new CellHolder();
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.listitem_adapter, null);

            holder.categoryTitle = (TextView)convertView.findViewById(R.id.list_item_adapter_textview);
            holder.categoryid = (TextView)convertView.findViewById(R.id.list_item_adapter_id);

            convertView.setTag(holder);
        }else{
            holder = (CellHolder) convertView.getTag();
        }
        Category category = null;
        try {
            category = (Category) data.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(category != null){
            holder.categoryTitle.setText(category.title);
            holder.categoryid.setText(category.id);
        }
        return convertView;
    }
    class CellHolder {

        TextView categoryTitle;
        TextView categoryid;

    }
}
