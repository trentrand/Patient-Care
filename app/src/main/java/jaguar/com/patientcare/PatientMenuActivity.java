package jaguar.com.patientcare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

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
        setContentView(R.layout.activity_login);

        // Initialize pointer to UI elements
        //btnLogin = (Button) findViewById(R.id.btnLogin);
        //btnForgotPassword = (Button) findViewById(R.id.btnForgotPassword);

        // Enable local datastore feature
        Parse.enableLocalDatastore(this);

        // Register custom ParseObject subclasses
        ParseObject.registerSubclass(Symptoms.class);

        // Initialize Parse
        Parse.initialize(this, "VewvxmlYofAlQkLcRUZjjfJSVf0U9WKeWpWcqfwJ", "K8hbr0l4agNpyJKvRvhmjEBSgkPcvTNrqTVYmxz8");

        // Register application to log app opens
        ParseAnalytics.trackAppOpened(getIntent());

        // See if we can skip the login view by checking for a cached user session
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            Intent symptomsIntent = new Intent(getApplicationContext(), SymptomsActivity.class);
            startActivity(symptomsIntent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void btnEmergency(){

    }

    public void btnInputPainLevels(){

    }

    public void btnViewPastRecords(){

    }
}
