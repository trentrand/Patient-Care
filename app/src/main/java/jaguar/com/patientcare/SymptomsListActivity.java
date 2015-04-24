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
import com.parse.ParseUser;
import com.parse.ParseQuery;
import com.parse.FindCallback;
import com.parse.ParseException;


import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by graemedrucker on 4/23/15.
 */
public class SymptomsListActivity extends ActionBarActivity {

    private ParseQuery<ParseObject> query;
    private ListView listSymptoms;
    private List<ParseObject> symptoms;
    public ParseObject clickedSymptom;

    //public SymtomsListActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms_list);
        listSymptoms = (ListView) findViewById(R.id.listSymptoms);

        query = ParseQuery.getQuery("Symptoms");
        query.orderByDescending("updatedAt"); //most recent at the top
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> symptomList, ParseException e) {
                if(e == null){
                    Log.d("Results", "Retrieved " + symptomList.size() + " results");
                    symptoms = symptomList;
                    refreshDisplay(symptoms);
                }
                else {
                    Log.d("Results", "Error: " + e.getMessage());
                    toastExceptionObject(e); //throw exception
                }
            }
        });

        listSymptoms.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                Intent symptomSummaryIntent = new Intent(getApplicationContext(), SymptomsSummaryActivity.class);

                // Hold static reference to ParseUser clicked
                // PatientSummaryActivity can retrieve the static reference
                try {
                    clickedSymptom = symptoms.get(position).fetchIfNeeded();
                } catch (Exception e) {
                    toastExceptionObject(e); //throw exception
                }

                String full = clickedSymptom.getUpdatedAt().toString();
                String date = full.substring(0, full.indexOf(":") - 3);
                String hour = full.substring(full.indexOf(":") - 2, full.indexOf(":"));
                String mins = full.substring(full.indexOf(":") + 1, full.indexOf(":") + 3);
                String am_pm = "am";
                int h = Integer.parseInt(hour);
                if((h-12) >= 0){
                    h = h-12;
                    am_pm = "pm";
                }

                //sends the patient's full name to the next activity
                symptomSummaryIntent.putExtra("dCom", clickedSymptom.get("comments").toString());
                symptomSummaryIntent.putExtra("date", date + " - " + h + ":" + mins + am_pm);
                symptomSummaryIntent.putExtra("emer", clickedSymptom.get("emergency").toString());
                symptomSummaryIntent.putExtra("painl", clickedSymptom.get("painLevel").toString());
                symptomSummaryIntent.putExtra("fatiguel", clickedSymptom.get("fatigueLevel").toString());
                symptomSummaryIntent.putExtra("numbl", clickedSymptom.get("numbnessLevel").toString());
                symptomSummaryIntent.putExtra("spastl", clickedSymptom.get("spasticityLevel").toString());
                symptomSummaryIntent.putExtra("visionl", clickedSymptom.get("visionLevel").toString());
                symptomSummaryIntent.putExtra("dizzinessl", clickedSymptom.get("dizzinessLevel").toString());
                symptomSummaryIntent.putExtra("bladderl", clickedSymptom.get("bladderLevel").toString());
                symptomSummaryIntent.putExtra("cogl", clickedSymptom.get("cognitiveLevel").toString());
                symptomSummaryIntent.putExtra("emol", clickedSymptom.get("emotionalLevel").toString());
                startActivity(symptomSummaryIntent);
                //Toast.makeText(getApplicationContext(), clickedSymptom.getObjectId().toString(), Toast.LENGTH_LONG).show();
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
        if (id == R.id.logout) {
            ParseUser.logOut();
            Intent registerIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(registerIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *@param symptomsList
     */

    public void refreshDisplay(List<ParseObject> symptomsList) {
        // Create custom PatientListAdapter using the patientList
        SymptomsListAdapter listAdapter = new SymptomsListAdapter(this, symptomsList);
        // Set the ArrayAdapter as the ListView's adapter.
        listSymptoms.setAdapter(listAdapter);
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