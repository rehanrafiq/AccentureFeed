package com.example.android.accenturefeed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;


@SuppressWarnings("ALL")
public class CategoriesFragment extends Fragment {

    CategoriesAdapter mCategoriesAdapter;
           CategoriesAdapter adapter=null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.listview_fragment, container, false);

        SendPostCategoriesReqAsyncTask asyncTask = (SendPostCategoriesReqAsyncTask) new SendPostCategoriesReqAsyncTask(new SendPostCategoriesReqAsyncTask.AsyncResponse() {

            @Override
            public void processFinish(JSONArray categories) {
                JSONArray category=categories;
                adapter = new CategoriesAdapter(new CategoriesActivity(), R.layout.listitem_adapter,category, new CategoriesActivity());
//                mCategoriesAdapter = new CategoriesAdapter( getActivity(), // The current context (this activity)
//                        R.layout.listitem_adapter, // The name of the layout ID.
//                        output,
//                        new CategoriesActivity());
                ListView listView = (ListView) rootView.findViewById(R.id.listview_categories);
                listView.setAdapter(adapter);

            }
        }).execute();
        setHasOptionsMenu(true);
        return rootView;
    }


}
