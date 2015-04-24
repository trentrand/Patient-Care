package jaguar.com.patientcare;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by graemedrucker on 4/23/15.
 */

public class SymptomsSummaryActivity extends ActionBarActivity {

    private String painlvl,fatiguelvl, numbnesslvl, spasticitylvl, visionlvl;
    private String dizzinesslvl, bladderlvl, cognitivelvl, emotionallvl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms_summary);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            setTitle(extras.getString("date"));
            painlvl = extras.getString("painl");
            fatiguelvl = extras.getString("fatiguel");
            numbnesslvl = extras.getString("numbl");
            spasticitylvl = extras.getString("spastl");
            visionlvl = extras.getString("visionl");
            dizzinesslvl = extras.getString("dizzinessl");
            bladderlvl = extras.getString("bladderl");
            cognitivelvl = extras.getString("cogl");
            emotionallvl = extras.getString("emol");
        }

        final TextView painChange = (TextView) findViewById(R.id.pain);
        final TextView fatigueChange = (TextView) findViewById(R.id.fatigue);
        final TextView numbnessChange = (TextView) findViewById(R.id.numbness);
        final TextView spasticityChange = (TextView) findViewById(R.id.spasticity);
        final TextView visionChange = (TextView) findViewById(R.id.vision);
        final TextView dizzinessChange = (TextView) findViewById(R.id.dizziness);
        final TextView bladderChange = (TextView) findViewById(R.id.bladder);
        final TextView cognitiveChange = (TextView) findViewById(R.id.cognitive);
        final TextView emotionalChange = (TextView) findViewById(R.id.emotional);

        painChange.setText("Pain: " + painlvl);
        fatigueChange.setText("Fatigue: " + fatiguelvl);
        numbnessChange.setText("Numbness: " + numbnesslvl);
        spasticityChange.setText("Spasticity: " + spasticitylvl);
        visionChange.setText("Vision: " + visionlvl);
        dizzinessChange.setText("Dizziness: " + dizzinesslvl);
        bladderChange.setText("Bladder: " + bladderlvl);
        cognitiveChange.setText("Cognitive: " + cognitivelvl);
        emotionalChange.setText("Emotional: " + emotionallvl);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_symptoms_summary, menu);
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

    private void toastExceptionObject(Exception e) {
        // Retrieve error message from e : Exception
        String errorMessage = e.getLocalizedMessage();

        // Capitalize the first letter
        errorMessage = errorMessage.substring(0, 1).toUpperCase() + errorMessage.substring(1, errorMessage.length());

        // Display toast with error message
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
    }
}
