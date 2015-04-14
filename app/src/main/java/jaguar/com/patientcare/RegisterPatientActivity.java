package jaguar.com.patientcare;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.EditText;

import com.parse.ParseUser;
import com.parse.*;

/**
 * Created by graemedrucker on 4/13/15.
 */
public class RegisterPatientActivity extends ActionBarActivity{
    private String email;
    private String password;
    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnRegister;
    //private Spinner spnUserType;
    private ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_patient);
        
        txtEmail = (EditText) findViewById(R.id.regEmail);
        txtPassword = (EditText) findViewById(R.id.regPassword);
        btnRegister = (Button) findViewById(R.id.regButton);
        //spnUserType = (Spinner) findViewById(R.id.regSpinner);

        btnRegister.setOnClickListener(new View.OnClickListener(){
	        @Override
	        public void onClick(View v){
		        btnRegister();
	        }
        });

        Parse.initialize(this, "VewvxmlYofAlQkLcRUZjjfJSVf0U9WKeWpWcqfwJ", "K8hbr0l4agNpyJKvRvhmjEBSgkPcvTNrqTVYmxz8");
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
	    getMenuInflater().inflate(R.menu.menu_register, menu);
	    return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
	    // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
	    int id = item.getItemId();
	    
	    //noinspection SimplifiableIfStatement
	    if(id == R.id.action_settings){
		    return true;
	    }
	    
	    return super.onOptionsItemSelected(item);
    }

    public void btnRegister(){
	    //get text from EditText fields
	    email = txtEmail.getText().toString().trim();
	    password = txtPassword.getText().toString().trim();
	    
	    //makes sure both fields are filled
	    if(email.isEmpty()){
		    txtEmail.setError("Please enter an email address");
		    return; //ends method without registration
	    }
	    if(password.isEmpty()){
		    txtPassword.setError("Please enter a password");
		    return; //ends method without registration
	    }

	    registerPatient(email, password); //register user if all fields are filled
    }

    //public void spnUserType(){
        //spinner stuff
    //}
    
    public void registerPatient(String em, String pass){
	    user = new ParseUser();
	    user.setUsername(em);
	    user.setEmail(em);
	    user.setPassword(pass);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Intent registerIntent = new Intent(getApplicationContext(),RegisterPatientActivity.class);
                    startActivity(registerIntent);
                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG).show();
                } else {
                    toastExceptionObject(e);
                }
            }
        });
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