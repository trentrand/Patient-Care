package jaguar.com.patientcare;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;


public class PatientSummaryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_summary);

        //gets the patient's name sent from PatientListActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            //sets the Actions Bar title to the patient's name
            setTitle(extras.getString("name"));
        }
        //sets the name to Nemo if none exists
        else setTitle("Nemo");
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
}
