package jaguar.com.patientcare;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.*;

public class PatientSummaryActivity extends ActionBarActivity {
    private ParseObject currentSymptom;

    //stores symptom levels
    private String objId, name, comment;

    //Pointers to UI elements
    private Button btnDequeue, btnSubmit;
    private EditText txtComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_summary);

        btnDequeue = (Button) findViewById(R.id.dequeueButton);
        txtComment = (EditText) findViewById(R.id.commentBox);
        btnSubmit = (Button) findViewById(R.id.submitButton);



        //gets the patient's name sent from PatientListActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //sets the Actions Bar title to the patient's name
            name = extras.getString("name");
            setTitle(name);
            objId = extras.getString("oId");
        }
        //sets the name to Nemo if none exists
        else setTitle("Nemo");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Symptoms");
        query.getInBackground(objId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject symptom, ParseException e) {
                if (e == null) {
                    currentSymptom = symptom;
                    setup(currentSymptom);
                } else {
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = txtComment.getText().toString().trim();
                if(comment.isEmpty()){
                    txtComment.setError("Comment box is empty.");
                }
                else{
                    currentSymptom.put("comments", comment);
                    currentSymptom.saveInBackground();
                    Toast.makeText(getApplicationContext(), "Comment Submitted", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDequeue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSymptom.put("queued", false);
                currentSymptom.saveInBackground();
                Toast.makeText(getApplicationContext(), name + " Dequeued", Toast.LENGTH_LONG).show();
                Intent patientListIntent = new Intent(getApplicationContext(),PatientListActivity.class);
                startActivity(patientListIntent);
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_patient_summary, menu);
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

    public void setup(ParseObject s){
        TextView emergencyChange = (TextView) findViewById(R.id.emergency);
        TextView painChange = (TextView) findViewById(R.id.pain);
        TextView fatigueChange = (TextView) findViewById(R.id.fatigue);
        TextView numbnessChange = (TextView) findViewById(R.id.numbness);
        TextView spasticityChange = (TextView) findViewById(R.id.spasticity);
        TextView visionChange = (TextView) findViewById(R.id.vision);
        TextView dizzinessChange = (TextView) findViewById(R.id.dizziness);
        TextView bladderChange = (TextView) findViewById(R.id.bladder);
        TextView cognitiveChange = (TextView) findViewById(R.id.cognitive);
        TextView emotionalChange = (TextView) findViewById(R.id.emotional);

        if(s.get("emergency") == false) emergencyChange.setText("Emergency: No");
        else emergencyChange.setText("Emergency: Yes");
        painChange.setText("Pain: " + s.get("painLevel").toString());
        fatigueChange.setText("Fatigue: " + s.get("fatigueLevel").toString());
        numbnessChange.setText("Numbness: " + s.get("numbnessLevel").toString());
        spasticityChange.setText("Spasticity: " + s.get("spasticityLevel").toString());
        visionChange.setText("Vision: " + s.get("visionLevel").toString());
        dizzinessChange.setText("Dizziness: " + s.get("dizzinessLevel").toString());
        bladderChange.setText("Bladder: " + s.get("bladderLevel").toString());
        cognitiveChange.setText("Cognitive: " + s.get("cognitiveLevel").toString());
        emotionalChange.setText("Emotional: " + s.get("emotionalLevel").toString());
    }
}
