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

import com.parse.*;

/**
 * Created by graemedrucker on 4/13/15.
 */
public class RegisterPatientActivity extends ActionBarActivity{
	
	//Stores input from the EditText fields
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userType;
	
	//Pointer to UI elements
    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnRegister;
    private Spinner spnUserType;
    
    //the new ParseUser
    private ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_patient);
        
        //Initialze pointer to UI elements
        txtFirstName = (EditText) findViewById(R.id.regFirstName);
        txtLastName = (EditText) findViewById(R.id.regLastName);
        txtEmail = (EditText) findViewById(R.id.regEmail);
        txtPassword = (EditText) findViewById(R.id.regPassword);
        btnRegister = (Button) findViewById(R.id.regButton);
        spnUserType = (Spinner) findViewById(R.id.regSpinner);
        
        //calls btnRegister() if the 'Register' button is pressed
        btnRegister.setOnClickListener(new View.OnClickListener(){
	        @Override
	        public void onClick(View v){
		        btnRegister();
	        }
        });

        //Initializes the correct Parse database
        Parse.initialize(this, "VewvxmlYofAlQkLcRUZjjfJSVf0U9WKeWpWcqfwJ", "K8hbr0l4agNpyJKvRvhmjEBSgkPcvTNrqTVYmxz8");
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
	    // Inflate the menu; this adds items to the action bar if it is present.
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
	    if(id == R.id.logout){
            ParseUser.logOut();
            Intent registerIntent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(registerIntent);
            finish();
	    }
	    return super.onOptionsItemSelected(item);
    }

    //gets the text from EditText fields and calls registerPatient() with them
    //as arguments when the 'Register' button is pressed
    public void btnRegister(){
	    //get text from EditText fields
        firstName = txtFirstName.getText().toString().trim();
        lastName = txtLastName.getText().toString().trim();
        email = txtEmail.getText().toString().trim();
	    password = txtPassword.getText().toString().trim();
        userType = spnUserType.getSelectedItem().toString();
	    
	    //makes sure all fields are filled
        if(firstName.isEmpty()){
            txtFirstName.setError("Please enter your first name");
            return; //ends method without registration
        }
        if(lastName.isEmpty()){
            txtLastName.setError("Please enter your last name");
            return; //ends method without registration
        }
        if(email.isEmpty()){
		    txtEmail.setError("Please enter an email address");
		    return; //ends method without registration
	    }
	    if(password.isEmpty()){
		    txtPassword.setError("Please enter a password");
		    return; //ends method without registration
	    }

	    registerPatient(firstName, lastName, email, password); //register user if all fields are filled
    }

    //creates a new ParseUser with the entered credentials
    public void registerPatient(String fn, String ln, String em, String pass){
	    
	    //creates the new ParseUser
	    user = new ParseUser();
	    
	    //sets the username, password, email, and userType
        user.put("firstName", fn);
        user.put("lastName", ln);
	    user.setUsername(em);
	    user.setEmail(em);
	    user.setPassword(pass);
        switch (userType){
            case "Patient":
                user.put("userType", 0);
                break;
            case "Doctor":
                user.put("userType", 1);
                break;
            case "Admin":
                user.put("userType", 2);
                break;
            default:
                user.put("userType", 0);
                break;
        }
        //returns to a blank registration page if the user was
        //successfully made, throws an exception otherwise
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Intent registerIntent = new Intent(getApplicationContext(),RegisterPatientActivity.class);
                    startActivity(registerIntent);
                    finish();
                    
                    //display 'Success' toast
                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG).show();
                } else {
                    toastExceptionObject(e); //throw exception
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