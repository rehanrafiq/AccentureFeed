package com.example.android.accenturefeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


class CategoriesAdapter extends BaseAdapter {
        ArrayList<Category> myList = new ArrayList<>();
        LayoutInflater inflater;
        Context context;

        public CategoriesAdapter(Context context, ArrayList<Category> myList) {
            this.myList = myList;
            this.context = context;
            inflater = LayoutInflater.from(this.context);
        }

        @Override
        public int getCount() {
            return myList.size();
        }

        @Override
        public Category getItem(int position) {
            return myList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder mViewHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_categories_adapter, parent, false);
                mViewHolder = new MyViewHolder(convertView);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (MyViewHolder) convertView.getTag();
            }

            Category currentListData = getItem(position);
//            position+=1;
//            ""+position+" : "+
            mViewHolder.titletext.setText(currentListData.getTitle());

            return convertView;
        }

    private class MyViewHolder {
            TextView titletext;

            public MyViewHolder(View item) {
                titletext = (TextView) item.findViewById(R.id.list_category_adapter_textview);
            }
        }
    }
