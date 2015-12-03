package org.pltw.examples.collegeapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by wdumas on 2/11/2015.
 */
public class Guardian extends FamilyMember implements ApplicantData {
    protected String mOccupation;
    public static final String JSON_OCCUPATION = "occupation";
    public static final String DEFAULT_OCCUPATION = "unknown";

    public int compareTo(FamilyMember familyMember) {
        if (this.mFirstName.equals(familyMember.mFirstName) && this.mLastName.equals(familyMember.mLastName)) {
            return 0;
        }
        return 1;
    }

    public Guardian(){
        mRelation = super.GUARDIAN;
        mFirstName = "Roger";
        mLastName = "Rabbit";
        mOccupation = "Professional Rabbit";
    }

    public Guardian(String firstName, String lastName) {
        mRelation = super.GUARDIAN;
        mFirstName = firstName;
        mLastName = lastName;
        mOccupation = DEFAULT_OCCUPATION;
    }

    public Guardian(String firstName, String lastName, String occupation) {
        mRelation = super.GUARDIAN;
        mFirstName = firstName;
        mLastName = lastName;
        mOccupation = occupation;
     }

    public Guardian(JSONObject json) throws JSONException {
        mRelation = super.GUARDIAN;
        mFirstName = json.getString(JSON_FIRST_NAME);
        mLastName = json.getString(JSON_LAST_NAME);
        mOccupation = json.getString(JSON_OCCUPATION);
    }

    public String getOccupation() {
        return mOccupation;
    }

    public void setOccupation(String occupation) {
        mOccupation = occupation;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = super.toJSON();
        json.put(JSON_OCCUPATION, mOccupation);
        return json;
    }

    public String toString () {
        return "Guardian: " + mFirstName + " " + mLastName + "\nOccupation: " + mOccupation;
    }
}
