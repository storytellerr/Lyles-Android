package grinnell.appdev.edu.lyles.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import grinnell.appdev.edu.lyles.AsyncRetrieval;
import grinnell.appdev.edu.lyles.ItemAdapter;
import grinnell.appdev.edu.lyles.JSONConstants;
import grinnell.appdev.edu.lyles.MenuItem;
import grinnell.appdev.edu.lyles.R;

/**
 * A fragment to display available hot food to user
 */
public class HotFoodFragment extends Fragment {

    private AsyncRetrieval mAsyncRetrieval;
    private String mJsonBody;
    private JSONArray mAllItemsAsJsonArray;

    private ArrayList<MenuItem> mMenuItemArrayList;
    private ItemAdapter mItemAdapter;
    private ListView mListView;

    public HotFoodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_food, container, false);

        mAsyncRetrieval = new AsyncRetrieval(JSONConstants.HOT_FOOD_URL);

        try {
            mJsonBody = mAsyncRetrieval.execute().get();
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
        catch(ExecutionException e) {
            e.printStackTrace();
        }

        mAllItemsAsJsonArray = mAsyncRetrieval.getJsonArray(mJsonBody, JSONConstants.HOT_FOOD_ARRAY_KEY);
        mMenuItemArrayList = MenuItem.fromJSON(mAllItemsAsJsonArray);

        mItemAdapter = new ItemAdapter(this.getContext(), mMenuItemArrayList, false);
        mListView = (ListView) view.findViewById(R.id.lv_items_hotfood);
        mListView.setAdapter(mItemAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAsyncRetrieval = null;
        mJsonBody = null;
        mAllItemsAsJsonArray = null;

        mMenuItemArrayList = null;
        mItemAdapter = null;
        mListView = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAsyncRetrieval = null;
        mJsonBody = null;
        mAllItemsAsJsonArray = null;

        mMenuItemArrayList = null;
        mItemAdapter = null;
        mListView = null;
    }
}
