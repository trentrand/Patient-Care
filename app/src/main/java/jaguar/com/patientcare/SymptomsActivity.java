package jaguar.com.patientcare;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;


public class SymptomsActivity extends ActionBarActivity {
    // Pointer to UI elements
    private RatingBar ratePain;
    private RatingBar rateFatigue;
    private RatingBar rateNumbness;
    private RatingBar rateSpasticity;
    private RatingBar rateVision;
    private RatingBar rateDizziness;
    private RatingBar rateBladder;
    private RatingBar rateCognitive;
    private RatingBar rateEmotional;
    private Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        // Initialize pointer to UI elements
        ratePain = (RatingBar) findViewById(R.id.ratePainRating);
        rateFatigue = (RatingBar) findViewById(R.id.rateFatigueRating);
        rateNumbness = (RatingBar) findViewById(R.id.rateNumbnessRating);
        rateSpasticity = (RatingBar) findViewById(R.id.rateSpasticityRating);
        rateVision = (RatingBar) findViewById(R.id.rateVisionRating);
        rateDizziness = (RatingBar) findViewById(R.id.rateDizzinessRating);
        rateBladder = (RatingBar) findViewById(R.id.rateBladderRating);
        rateCognitive = (RatingBar) findViewById(R.id.rateCognitiveRating);
        rateEmotional = (RatingBar) findViewById(R.id.rateEmotionalRating);
        btnDone = (Button) findViewById(R.id.btnDone);

        // Declare UI element event handlers
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDone();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_symptoms, menu);
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
     * Called when btnDone is clicked on activity_symptoms.
     * Creates a userSymptoms: Symptoms object
     * userSymptoms object is saved in background to Parse database
     */
    public void btnDone() {
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
        userSymptoms.put("emergency", false);
        userSymptoms.setPainLevel((int) ratePain.getRating());
        userSymptoms.setFatigueLevel((int) rateFatigue.getRating());
        userSymptoms.setNumbnessLevel((int) rateNumbness.getRating());
        userSymptoms.setSpasticityLevel((int) rateSpasticity.getRating());
        userSymptoms.setVisionLevel((int) rateVision.getRating());
        userSymptoms.setDizzinessLevel((int) rateDizziness.getRating());
        userSymptoms.setBladderLevel((int) rateBladder.getRating());
        userSymptoms.setCognitiveLevel((int) rateCognitive.getRating());
        userSymptoms.setEmotionalLevel((int) rateEmotional.getRating());
        userSymptoms.setSymptomCount();
        userSymptoms.put("queued", true);
        userSymptoms.put("comments", "");
        Calendar c = Calendar.getInstance();
        int sortKey = c.get(Calendar.SECOND);
        userSymptoms.put("sortKey", sortKey);

        // Store the users highest symptom string in ParseUser object for use on PatientListActivity adn PatientSummaryActivity
        ParseUser.getCurrentUser().put("highestSymptom", userSymptoms.getHighestSymptom());

        Toast.makeText(getApplicationContext(), "Symptoms saved to database", Toast.LENGTH_LONG).show();
        userSymptoms.saveInBackground();
        btnDone.setEnabled(false);
        Intent menuIntent = new Intent(getApplicationContext(),PatientMenuActivity.class);
        startActivity(menuIntent);
    }
}
