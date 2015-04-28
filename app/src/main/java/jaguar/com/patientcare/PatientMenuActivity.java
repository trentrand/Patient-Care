package jaguar.com.patientcare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Calendar;
import java.util.List;

public class PatientMenuActivity extends ActionBarActivity {

    // Pointer to UI elements
    private Button btnEmergency;
    private Button btnInputPainLevels;
    private Button btnViewPastRecords;

    // Holds user's information
    private ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_menu_activity);

        // Initialize pointer to UI elements
        btnEmergency = (Button) findViewById(R.id.btnEmergency);
        btnInputPainLevels = (Button) findViewById(R.id.btnInputPainLevels);
        btnViewPastRecords = (Button) findViewById(R.id.btnViewPastRecords);

        // Declare UI element event handlers
        btnEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEmergency();
            }
        });

        btnInputPainLevels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnInputPainLevels();
            }
        });

        btnViewPastRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnViewPastRecords();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    public void btnEmergency(){
        ParseQuery query = ParseQuery.getQuery("Symptoms");
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> symptomList, com.parse.ParseException e) {
                if(e == null){
                    if(symptomList.size() != 0){
                    for(int i = 0; i < symptomList.size(); i++){
                        ParseObject p  = symptomList.get(i);
                        p.put("queued", false);
                        p.saveInBackground();
                    }}
                    //refreshDisplay(patients);
                }
                else {
                    //Log.d("Symptoms", "Error: " + e.getMessage());
                }
            }
        });

        Symptoms userSymptoms = new Symptoms();
        userSymptoms.setUser(ParseUser.getCurrentUser());
        userSymptoms.put("emergency", true);
        userSymptoms.setPainLevel(0);
        userSymptoms.setFatigueLevel(0);
        userSymptoms.setNumbnessLevel(0);
        userSymptoms.setSpasticityLevel(0);
        userSymptoms.setVisionLevel(0);
        userSymptoms.setDizzinessLevel(0);
        userSymptoms.setBladderLevel(0);
        userSymptoms.setCognitiveLevel(0);
        userSymptoms.setEmotionalLevel(0);
        userSymptoms.setSymptomCount();
        userSymptoms.put("queued", true);
        userSymptoms.put("comments", "");
        Calendar c = Calendar.getInstance();
        int sortKey = c.get(Calendar.SECOND);
        userSymptoms.put("sortKey", sortKey + 2000000000);

        // Store the users highest symptom string in ParseUser object for use on PatientListActivity adn PatientSummaryActivity
        ParseUser.getCurrentUser().put("highestSymptom", userSymptoms.getHighestSymptom());

        Toast.makeText(getApplicationContext(), "Emergency Situation Recorded", Toast.LENGTH_LONG).show();
        userSymptoms.saveInBackground();
        btnEmergency.setEnabled(false);
        Intent menuIntent = new Intent(getApplicationContext(),PatientMenuActivity.class);
        startActivity(menuIntent);
        finish();
    }

    public void btnInputPainLevels(){
        //goto symptoms activity screen!
        Intent symptomsIntent = new Intent(getApplicationContext(), SymptomsActivity.class);
        startActivity(symptomsIntent);
    }

    public void btnViewPastRecords(){
        //view past history stuff.
        Intent symptomsIntent = new Intent(getApplicationContext(), SymptomsListActivity.class);
        startActivity(symptomsIntent);
    }
}
