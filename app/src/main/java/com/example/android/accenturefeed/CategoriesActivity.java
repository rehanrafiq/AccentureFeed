package com.example.android.accenturefeed;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


@SuppressWarnings("ALL")
    public class CategoriesActivity extends AppCompatActivity {



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.categories_activity);
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new CategoriesFragment())
                        .commit();


            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up loginbutton, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
//         CategoriesAdapter adapter;
//
//        public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
//                                 Bundle savedInstanceState) {
//            final View rootView = inflater.inflate(R.layout.listview_fragment, container, false);
//
//            SendPostCategoriesReqAsyncTask asyncTask = (SendPostCategoriesReqAsyncTask) new SendPostCategoriesReqAsyncTask(new SendPostCategoriesReqAsyncTask.AsyncResponse() {
//                @Override
//                public void processFinish(ArrayList output) {
////                mCategoriesAdapter = new ArrayAdapter<String>(
////                        getActivity(), // The current context (this activity)
////                        R.layout.listitem_adapter, // The name of the layout ID.
////                        R.id.list_item_adapter_textview, // The ID of the textview to populate.
////                        output
////                );
//                    adapter = new CategoriesAdapter(new CategoriesActivity(), R.layout.listitem_adapter,output, new CategoriesActivity());
//
////                    CategoriesAdapter mCategoriesAdapter = new CategoriesAdapter(getActivity(), // The current context (this activity)
////                            R.layout.listitem_adapter, // The name of the layout ID.
////                            output,
////                            new CategoriesActivity());
//                    ListView listView = (ListView) rootView.findViewById(R.id.listview_categories);
//                    listView.setAdapter(adapter);
//
//                }
//            }).execute();
//            return rootView;
//        }


}




//class Category{
//    public String title;
//    public String id;
//    public Category(JSONObject object){
//        try {
//            this.title = object.getString("title");
//            this.id = object.getString("_id");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//    public static ArrayList<Category> fromJson(JSONArray jsonObjects) {
//        ArrayList<Category> users = new ArrayList<>();
//        for (int i = 0; i < jsonObjects.length(); i++) {
//            try {
//                users.add(new Category(jsonObjects.getJSONObject(i)));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        return users;
//    }
//
//}
//
//class CategoryAdapter extends ArrayAdapter<Category> {
//    public CategoryAdapter(Context context, ArrayList<Category> categories) {
//        super(context, 0, categories);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Get the data item for this position
//        Category category = getItem(position);
//        // Check if an existing view is being reused, otherwise inflate the view
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_adapter, parent, false);
//        }
//        // Lookup view for data population
//        TextView textView = (TextView) convertView.findViewById(R.id.list_item_adapter_textview);
////        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
//        // Populate the data into the template view using the data object
//        textView.setText(category.title);
//
////        tvHome.setText(user.hometown);
//        // Return the completed view to render on screen
//        return convertView;
//    }
//
//}
//
