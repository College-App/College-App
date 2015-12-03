package org.pltw.examples.collegeapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wdumas on 8/10/2015.
 */
public class Family implements ApplicantData {
    private static final String TAG = Family.class.getName();
    public static final String JSON_FAMILY = "family";
    private ArrayList<FamilyMember> mFamily;
    private static Family sFamily; //singleton

    private Family () {
        mFamily = new ArrayList<FamilyMember>();
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONArray jArr = new JSONArray();
        for(FamilyMember f : mFamily) {
            JSONObject json = f.toJSON();
            if(f.getRelation() == 0)
                json.put(Guardian.JSON_OCCUPATION, ((Guardian)f).getOccupation());
            jArr.put(json);
        }
        JSONObject json = new JSONObject();
        json.put(JSON_FAMILY, jArr);
        return json;
    }


    public static Family get(){
        if (sFamily == null) {
            sFamily = new Family();
        }
        return sFamily;
    }

    public ArrayList<FamilyMember> getFamily() {
        return mFamily;
    }

    public void setFamily(ArrayList<FamilyMember> family) {
        mFamily = family;
    }

    public void setFamily(JSONObject json) throws JSONException {
        JSONArray jArr =  new JSONArray(json.getJSONArray(Family.JSON_FAMILY).toString());
        ArrayList<FamilyMember> familyMembers = new ArrayList<FamilyMember>();
        for(int i = 0; i < jArr.length(); i++) {
            JSONObject memberObj = (JSONObject)jArr.get(i);
            /*String firstName = memberObj.getString(FamilyMember.JSON_FIRST_NAME);
            String lastName = memberObj.getString(FamilyMember.JSON_FIRST_NAME);
            String occupation = "";*/
            int relation = memberObj.getInt(FamilyMember.JSON_RELATION);
            if(relation == FamilyMember.GUARDIAN) {
                //occupation = memberObj.getString(Guardian.JSON_OCCUPATION);
                Guardian g = new Guardian(memberObj);
                familyMembers.add(g);
            }
            else {
                Sibling s = new Sibling(memberObj);
                familyMembers.add(s);
            }
        }
        Log.e(TAG, familyMembers.toString());
        mFamily = familyMembers;
    }

    public void addFamilyMember(FamilyMember familyMember) {
        Log.d(TAG, "Adding " + familyMember);
        mFamily.add(familyMember);
    }

    public void deleteFamilyMember(FamilyMember familyMember) {
        Log.d(TAG, "Deleting " + familyMember);
        mFamily.remove(familyMember);
    }
}
