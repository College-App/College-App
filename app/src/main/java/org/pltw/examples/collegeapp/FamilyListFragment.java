package org.pltw.examples.collegeapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wdumas on 8/14/2015.
 */
public class FamilyListFragment extends ListFragment {
    private static final String TAG = FamilyListFragment.class.getName();
    private static final String FILENAME = "family.json";
    private Family mFamily;
    private FamilyJSONStorer mStorer;
    private Context mAppContext;

    public FamilyListFragment () {
        mFamily = Family.get();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.family_members_title);
        mAppContext = this.getActivity();
        mStorer = new FamilyJSONStorer(mAppContext, FILENAME);
        FamilyMemberAdapter adapter = new FamilyMemberAdapter(mFamily.getFamily());
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);

        ListView listView = (ListView)v.findViewById(android.R.id.list);
        registerForContextMenu(listView);
        loadFamily();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_family_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FamilyMemberAdapter adapter = (FamilyMemberAdapter)getListAdapter();
        boolean matchFlag = false;
        switch (item.getItemId()) {
            case R.id.menu_item_new_guardian:
                Log.d(TAG, "Selected add new guardian.");
                Guardian guardian = new Guardian();
                for (FamilyMember f : Family.get().getFamily()) {
                    /*if (f == guardian) {
                        Log.i(TAG, "Possible match " + guardian + " and " + f);
                    }*/
                    if (f.compareTo((FamilyMember)guardian) == 0) {
                        Log.i(TAG, "Possible match " + guardian + " and " + f);
                        matchFlag = true;
                    }
                }
                if (!matchFlag) {
                    Log.i(TAG, "Adding " + guardian + " to the family");
                    Family.get().addFamilyMember(guardian);
                    saveFamily();
                }
                adapter.clear();
                adapter.addAll(Family.get().getFamily());
                adapter.notifyDataSetChanged();
                return true;
            case R.id.menu_item_new_sibling:
                Log.d(TAG, "Selected add new sibling.");
                Sibling sibling = new Sibling();
                for (FamilyMember f : Family.get().getFamily()) {
                    if (f.compareTo((FamilyMember)sibling) == 0) {
                        Log.i(TAG, "Possible match " + sibling + " and " + f);
                        matchFlag = true;
                    }
                }
                if (!matchFlag) {
                    Log.i(TAG, "Adding " + sibling + " to the family");
                    Family.get().addFamilyMember(sibling);
                    saveFamily();
                }
                adapter.clear();
                adapter.addAll(Family.get().getFamily());
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        Log.d(TAG, "Creating Context Menu.");
        getActivity().getMenuInflater().inflate(R.menu.family_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d(TAG, "Context item selected.");
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        FamilyMemberAdapter adapter = (FamilyMemberAdapter)getListAdapter();
        FamilyMember familyMember = adapter.getItem(position);

        switch (item.getItemId()) {
            case R.id.menu_item_delete_family_member:
                Family.get().deleteFamilyMember(familyMember);
                saveFamily();
                adapter.clear();
                adapter.addAll(Family.get().getFamily());
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private class FamilyMemberAdapter extends ArrayAdapter<FamilyMember> {
        public FamilyMemberAdapter(ArrayList<FamilyMember> family) {
            super(getActivity(), 0, family);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_family_member, null);
            }
            FamilyMember f = getItem(position);

            TextView nameTextView =
                    (TextView)convertView
                            .findViewById(R.id.family_member_list_item_nameTextView);
            nameTextView.setText(f.toString());
            Log.d(TAG, "The type of FamilyMember at position " + position + " is " + f.getClass().getName());

            return convertView;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        FamilyMember f = ((FamilyMemberAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, f.toString() + " was clicked." + FamilyMemberActivity.class);
        Intent i = new Intent(getActivity(), FamilyMemberActivity.class);
        i.putExtra(FamilyMember.EXTRA_RELATION, f.getRelation());
        i.putExtra(FamilyMember.EXTRA_INDEX, position);
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        FamilyMemberAdapter adapter = (FamilyMemberAdapter)getListAdapter();
        adapter.clear();
        adapter.addAll(Family.get().getFamily());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveFamily();
        Log.d(TAG, "Fragment paused.");
    }
    private void loadFamily() {
        try {
            mStorer.load();
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void saveFamily() {
        try {
            mStorer.save(mFamily);
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
