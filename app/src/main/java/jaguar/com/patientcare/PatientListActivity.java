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

    private ParseQuery<ParseUser> query;
    private ListView listPatients;
    private List<ParseUser> patients;
    private static ParseUser clickedUser;

    public PatientListActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        listPatients = (ListView) findViewById(R.id.listPatients);

        query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> patientList, ParseException e) {
                if (e == null) {
                    Log.d("Patients", "Retrieved " + patientList.size() + " users");
                    patients = patientList;
                    refreshDisplay(patientList);
                } else {
                    Log.d("Symptoms", "Error: " + e.getMessage());
                }
            }
        });

        listPatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Intent patientSummaryIntent = new Intent(getApplicationContext(), PatientSummaryActivity.class);

                // Hold static reference to ParseUser clicked
                // PatientSummaryActivity can retrieve the static reference
                try {
                    clickedUser = patients.get(position).fetchIfNeeded();
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }

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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Refreshes the list view with parameter List
     * @param patientList
     */
    public void refreshDisplay(List<ParseUser> patientList) {
        // Create custom PatientListAdapter using the patientList
        PatientListAdapter listAdapter = new PatientListAdapter(this, patientList);
        // Set the ArrayAdapter as the ListView's adapter.
        listPatients.setAdapter(listAdapter);

    }
}