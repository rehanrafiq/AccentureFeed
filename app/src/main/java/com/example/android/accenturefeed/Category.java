package com.example.android.accenturefeed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class Category{
    public String title;
    public String id;
    public Category(JSONObject object){
        try {
            this.title = object.getString("title");
            this.id = object.getString("_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Category> fromJson(JSONArray jsonObjects) {
        ArrayList<Category> users = new ArrayList<>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                users.add(new Category(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

}