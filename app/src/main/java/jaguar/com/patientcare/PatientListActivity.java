package jaguar.com.patientcare;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PatientListActivity extends ActionBarActivity {

    //private ParseQuery<ParseUser> query;
    private ParseQuery<ParseObject> query;
    private ListView listPatients;
    //private List<ParseUser> patients;
    private List<ParseObject> patients;

    public static ParseObject clickedUser;
    public ParseUser clickedU;

    public PatientListActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        listPatients = (ListView) findViewById(R.id.listPatients);

        /*
        query = ParseUser.getQuery();
        query.whereEqualTo("queued", true); //only show's patients waiting to be seen
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> patientList, ParseException e) {
                if (e == null) {
                    Log.d("Patients", "Retrieved " + patientList.size() + " users");
                    patients = patientList;
                    refreshDisplay(patients);
                } else {
                    Log.d("Symptoms", "Error: " + e.getMessage());
                }
            }
        });
        */

        query = ParseQuery.getQuery("Symptoms");
        query.orderByDescending("updatedAt");
        query.whereEqualTo("queued", true);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> symptomList, ParseException e) {
                if(e == null){
                    Log.d("Results", "Retrieved " + symptomList.size() + " results");
                    patients = symptomList;
                    refreshDisplay(patients);
                }
                else {
                    Log.d("Symptoms", "Error: " + e.getMessage());
                }
            }
        });

        
        listPatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Intent patientSummaryIntent = new Intent(getApplicationContext(), PatientSummaryActivity.class);

                // Hold static reference to ParseUser clicked
                // PatientSummaryActivity can retrieve the static reference
                try {
                    clickedUser = patients.get(position).fetchIfNeeded();
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }

                clickedU = (ParseUser)clickedUser.get("user");
                //sends the patient's full name to the next activity
                patientSummaryIntent.putExtra("name", clickedU.getString("firstName") + " " + clickedU.getString("lastName"));
                patientSummaryIntent.putExtra("oId", clickedUser.getObjectId());
/*
                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Symptoms");
                query1.orderByDescending("updatedAt");
                query1.whereEqualTo("user", clickedUser);
                query1.getFirstInBackground(new GetCallback<ParseObject>() {
                    //@Override
                    public void done(ParseObject symptom, ParseException e) {
                        if (e == null) {
                            patientSummaryIntent.putExtra("painl", symptom.get("painLevel").toString());
                            patientSummaryIntent.putExtra("fatiguel", symptom.get("fatigueLevel").toString());
                            patientSummaryIntent.putExtra("numbl", symptom.get("numbnessLevel").toString());
                            patientSummaryIntent.putExtra("spastl", symptom.get("spasticityLevel").toString());
                            patientSummaryIntent.putExtra("visionl", symptom.get("visionLevel").toString());
                            patientSummaryIntent.putExtra("dizzinessl", symptom.get("dizzinessLevel").toString());
                            patientSummaryIntent.putExtra("bladderl", symptom.get("bladderLevel").toString());
                            patientSummaryIntent.putExtra("cogl", symptom.get("cognitiveLevel").toString());
                            patientSummaryIntent.putExtra("emol", symptom.get("emotionalLevel").toString());
                        } else {
                        }
                    }
                });*/
                startActivity(patientSummaryIntent);
            }
        });
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_patient_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.logout){
            ParseUser.logOut();
            Intent registerIntent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(registerIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Refreshes the list view with parameter List
     * @param patientList
     */
    public void refreshDisplay(List<ParseObject> patientList) {
        // Create custom PatientListAdapter using the patientList
        PatientListAdapter listAdapter = new PatientListAdapter(this, patientList);
        // Set the ArrayAdapter as the ListView's adapter.
        listPatients.setAdapter(listAdapter);
    }
}