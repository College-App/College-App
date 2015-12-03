package org.pltw.examples.collegeapp;

import android.content.Context;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by wdumas on 6/25/2015.
 */
public abstract class JSONStorer {
    protected Context mContext;
    protected String mFilename;
    private static final int PROFILE = 0;

    public JSONStorer() {}

    public JSONStorer(Context c, String f) {
        mContext = c;
        mFilename = f;
    }

    public abstract ApplicantData load() throws IOException, JSONException;
    public abstract void save(ApplicantData applicantData) throws JSONException, IOException;
}
