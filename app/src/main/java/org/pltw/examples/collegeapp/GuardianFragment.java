package org.pltw.examples.collegeapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by wdumas on 2/18/2015.
 */
public class GuardianFragment extends FamilyMemberFragment {
    private static final String TAG = "GuardianFragment";
    private static final String FILENAME = "guardian.json";
    private Guardian mGuardian;
    private GuardianJSONStorer mStorer;
    private Context mAppContext;
    private TextView mOccupation;
    private EditText mEnterOccupation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppContext = this.getActivity();
        Log.d(TAG, "Context: " + mAppContext);
        mStorer = new GuardianJSONStorer(mAppContext, FILENAME);

        /*try {
            mGuardian = mStorer.load();
        } catch (Exception e) {
            mGuardian = new Guardian();
            Log.e(TAG, "Error loading guardian: " + FILENAME, e);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_guardian, container, false);

        mGuardian = (Guardian)Family.get().getFamily().get(getActivity().
                getIntent().getIntExtra(FamilyMember.EXTRA_INDEX,0));//new Guardian();

        mFirstName = (TextView)rootView.findViewById(R.id.first_name);
        mEnterFirstName = (EditText)rootView.findViewById(R.id.enter_first_name);
        mLastName = (TextView)rootView.findViewById(R.id.last_name);
        mEnterLastName = (EditText)rootView.findViewById(R.id.enter_last_name);
        mOccupation = (TextView)rootView.findViewById(R.id.occupation);
        mEnterOccupation = (EditText)rootView.findViewById(R.id.enter_occupation);

        mFirstName.setText(mGuardian.mFirstName);
        mLastName.setText(mGuardian.mLastName);
        mOccupation.setText(mGuardian.mOccupation);

        FirstNameTextChanger firstNameTextChanger = new FirstNameTextChanger();
        LastNameTextChanger lastNameTextChanger = new LastNameTextChanger();
        OccupationTextChanger occupationTextChanger = new OccupationTextChanger();

        mEnterFirstName.addTextChangedListener(firstNameTextChanger);
        mEnterLastName.addTextChangedListener(lastNameTextChanger);
        mEnterOccupation.addTextChangedListener(occupationTextChanger);

        return rootView;
    }

    private class FirstNameTextChanger implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mGuardian.setFirstName(s.toString());
            ArrayList<FamilyMember> family = Family.get().getFamily();
            int index = getActivity().getIntent().getIntExtra(FamilyMember.EXTRA_INDEX,0);
            family.set(index, (FamilyMember) mGuardian);
        }

        @Override
        public void afterTextChanged(Editable s) {
            mFirstName.setText(mGuardian.getFirstName());
        }
    }

    private class LastNameTextChanger implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mGuardian.setLastName(s.toString());
            ArrayList<FamilyMember> family = Family.get().getFamily();
            int index = getActivity().getIntent().getIntExtra(FamilyMember.EXTRA_INDEX,0);
            family.set(index, (FamilyMember) mGuardian);

        }


        @Override
        public void afterTextChanged(Editable s) {
            mLastName.setText(mGuardian.getLastName());
        }
    }

    private class OccupationTextChanger implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mGuardian.setOccupation(s.toString());
            ArrayList<FamilyMember> family = Family.get().getFamily();
            int index = getActivity().getIntent().getIntExtra(FamilyMember.EXTRA_INDEX,0);
            family.set(index, (FamilyMember) mGuardian);

        }


        @Override
        public void afterTextChanged(Editable s) {
            mOccupation.setText(mGuardian.getOccupation());
        }
    }

    private boolean saveGuardian() {
        try {
            mStorer.save(mGuardian);
            Log.d(TAG, "guardian saved to file.");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving guardian: ", e);
            return false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveGuardian();
        Log.d(TAG, "Fragment paused.");
    }
/*
    private void loadGuardian() {
        try {
            mGuardian = mStorer.load();
            Log.d(TAG, "Loaded " + mGuardian.getFirstName());
            mFirstName.setText(mGuardian.getFirstName());
            mLastName.setText(mGuardian.getLastName());
        } catch (Exception e) {
            mGuardian = new Guardian();
            Log.e(TAG, "Error loading guardian from: " + FILENAME, e);
        }
    }
*/
    @Override
    public void onResume() {
        super.onResume();
        //loadGuardian();
        Log.d(TAG, "Fragment resumed.");
    }

}
