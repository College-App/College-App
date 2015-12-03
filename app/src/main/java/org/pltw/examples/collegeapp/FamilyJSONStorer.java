package org.pltw.examples.collegeapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by gshorr on 11/2/15.
 */
public class FamilyJSONStorer extends JSONStorer {


        private static final String TAG = FamilyJSONStorer.class.getName();

        public FamilyJSONStorer(Context c, String f) {
            super(c, f);
        }

        public void save(ApplicantData applicantData) throws JSONException, IOException {
            if (applicantData instanceof Family) {
                Family family = Family.get();
                Writer writer = null;
                try {
                    Log.d(TAG, "Family in JSON: " + family.toJSON().toString() + " saved to: " +
                            mFilename);
                    OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
                    writer = new OutputStreamWriter(out);
                    writer.write(family.toJSON().toString());
                } finally {
                    if (writer != null)
                        writer.close();
                }
            }
        }

        public Family load() throws IOException, JSONException {
            BufferedReader reader = null;
            Family f = Family.get();
            try {
                Log.d(TAG, "Opening an input stream from: " + mFilename + " with Context:" +
                        mContext);
                InputStream in = mContext.openFileInput(mFilename);
                reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder jsonString = new StringBuilder();
                String line = null;
                while ( (line = reader.readLine()) != null) {
                    jsonString.append(line);
                }
                f.setFamily(new JSONObject(jsonString.toString()));
            } catch (FileNotFoundException e) {
                Log.e(TAG, "Error loading Family from: " + mFilename, e);
                f.setFamily(new ArrayList<FamilyMember>());
            } finally {
                if (reader != null)
                    reader.close();
            }
            return f;
        }

    }


