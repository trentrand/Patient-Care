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

import com.parse.ParseUser;

import java.text.ParseException;


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
        Symptoms userSymptoms = new Symptoms();
        userSymptoms.setUser(ParseUser.getCurrentUser());
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

        // Store the users highest symptom string in ParseUser object for use on PatientListActivity adn PatientSummaryActivity
        ParseUser.getCurrentUser().put("highestSymptom", userSymptoms.getHighestSymptom());
        ParseUser.getCurrentUser().put("queued", true);

        Toast.makeText(getApplicationContext(), "Symptoms saved to database", Toast.LENGTH_LONG).show();
        userSymptoms.saveInBackground();
        btnDone.setEnabled(false);
        Intent menuIntent = new Intent(getApplicationContext(),PatientMenuActivity.class);
        startActivity(menuIntent);
    }
}
