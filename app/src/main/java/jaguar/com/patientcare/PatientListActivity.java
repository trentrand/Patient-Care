package jaguar.com.patientcare;

import java.util.List;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


import android.widget.AdapterView;
import android.widget.ListView;

public class PatientListActivity extends ActionBarActivity {

    private ParseQuery<ParseObject> query;
    private ListView listPatients;
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