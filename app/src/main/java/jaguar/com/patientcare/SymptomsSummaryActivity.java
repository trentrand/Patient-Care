package jaguar.com.patientcare;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseException;

/**
 * Created by graemedrucker on 4/23/15.
 */

public class SymptomsSummaryActivity extends ActionBarActivity {

    private String objId, painlvl,fatiguelvl, numbnesslvl, spasticitylvl, visionlvl;
    private String dizzinesslvl, bladderlvl, cognitivelvl, emotionallvl;
    private TextView txtPain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms_summary);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            objId = extras.getString("objId");
        }
        else{objId = "null";}

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Symptoms");
        query.getInBackground(objId, new GetCallback<ParseObject>() {
            public void done(ParseObject currentSymptom, ParseException e) {
                if (e == null) {
                    painlvl = "test"; //currentSymptom.get("painLevel").toString();
                } else {
                    toastExceptionObject(e); //throw exception
                }
            }
        });

        setTitle(objId);
        final TextView painChange = (TextView) findViewById(R.id.pain);
        final TextView fatigueChange = (TextView) findViewById(R.id.fatigue);
        final TextView numbnessChange = (TextView) findViewById(R.id.numbness);
        final TextView spasticityChange = (TextView) findViewById(R.id.spasticity);
        final TextView visionChange = (TextView) findViewById(R.id.vision);
        final TextView dizzinessChange = (TextView) findViewById(R.id.dizziness);
        final TextView bladderChange = (TextView) findViewById(R.id.bladder);
        final TextView cognitiveChange = (TextView) findViewById(R.id.cognitive);
        final TextView emotionalChange = (TextView) findViewById(R.id.emotional);

        painChange.setText("Pain " + painlvl);
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
