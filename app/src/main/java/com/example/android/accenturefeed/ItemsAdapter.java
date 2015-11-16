package com.example.android.accenturefeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


class ItemsAdapter extends BaseAdapter {
    ArrayList<Items> myListItems = new ArrayList<>();
    LayoutInflater inflater;
    Context context;

    public ItemsAdapter(Context context, ArrayList<Items> myList) {
        this.myListItems = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myListItems.size();
    }

    @Override
    public Items getItem(int position) {
        return myListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_items_adapter, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        Items currentListData = getItem(position);
//        position+=1;
//        ""+position+" : "+

        mViewHolder.itemtitletext.setText(currentListData.getItemTitle());

        return convertView;
    }
    private class MyViewHolder {

        TextView itemtitletext;

        public MyViewHolder(View item) {
            itemtitletext = (TextView) item.findViewById(R.id.list_item_adapter_textview);
        }
    }
}
