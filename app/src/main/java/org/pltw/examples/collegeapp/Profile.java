package org.pltw.examples.collegeapp;


import android.content.Context;
import android.os.Environment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by wdumas on 12/22/2014.
 */
public class Profile implements ApplicantData{
    private static final String JSON_FIRST_NAME = "firstName";
    private static final String JSON_LAST_NAME = "lastName";
    private static final String JSON_DOB = "dob";
    private static final String JSON_GPA = "gpa";
    private static final String JSON_SAT = "sat";
    private static final String PHOTO_FILENAME = "IMG_PROFILE.jpg";

    private String mFirstName;
    private String mLastName;
    private Date mDateOfBirth;
    private double GPA;
    private int SAT;

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public int getSAT(){
        return SAT;
    }

    public void setSAT(int a){
        SAT = a;
    }

    public double getGPA(){
        return GPA;
    }

    public void setGPA(double a){
        GPA = a;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_FIRST_NAME, mFirstName);
        json.put(JSON_LAST_NAME, mLastName);
        json.put(JSON_DOB, mDateOfBirth.getTime());
        //json.put(JSON_GPA, GPA);
        //json.put(JSON_SAT, SAT);
        System.out.println("Date of Birth Saved: " + mDateOfBirth);
        return json;
    }

    public String dobToString() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        return df.format(mDateOfBirth.getTime());
    }

    public void setmDateOfBirth(Date mDateOfBirth) {
        this.mDateOfBirth = mDateOfBirth;
    }

    public Profile() {
        mFirstName = new String("Wyatt");
        mLastName = new String("Dumas");
        mDateOfBirth = new Date(83, 0, 24);
        GPA = 0;
        SAT = 0;
    }

    public Profile(JSONObject json) throws JSONException {
        mFirstName = json.getString(JSON_FIRST_NAME);
        mLastName = json.getString(JSON_LAST_NAME);
        mDateOfBirth = new Date(json.getLong(JSON_DOB));
        //GPA = Double.parseDouble(json.getString(JSON_GPA));
        //SAT = Integer.parseInt(json.getString(JSON_SAT));

    }

    public String toString() {
        return mFirstName + " " + mLastName + " " + mDateOfBirth.getTime();
    }

    public String getPhotoFileName(){

    }

    public File getPhotoFile(Context context){
        File externalFileDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if(externalFileDir == null) return null;
        return new File(externalFileDir, getPhotoFileName());
    }

}
