package jaguar.com.patientcare;

//import android.content.Intent;
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
    private String email;
    private String password;
    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnRegister;
    private Spinner spnUserType;
    private ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_patient);
        
        txtEmail = (EditText) findViewById(R.id.email);
        txtPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.button);
        spnUserType = (Spinner) findViewById(R.id.spinner);
        
        btnRegister.setOnClickListener(new View.OnClickListener(){
	        @Override
	        public void onClick(View v){
		        btnRegister();
	        }
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
	    getMenuInflater().inflate(R.menu.menu_login, menu);
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
	    email = (String) txtEmail.getText().toString().trim();
	    password = (String) txtPassword.getText().toString().trim();
	    //insert spinner stuff here
	    
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

    public void spnUserType(){
        //spinner stuff
    }
    
    public void registerPatient(String email, String password){
	    ParseUser user = new ParseUser();
	    user.setUsername(email);
	    user.setEmail(email);
	    user.setPassword(password);
	    
	    //user.put("userType", "data");
	    
	    //Toast.makeText(getApplicationContext(), password, Toast.LENGTH_SHORT).show();
	    
	    user.signUpInBackground(new SignUpCallback() {
		    @Override
		    public void done(ParseException e){
			    if(e == null) {
				    Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
			    }
			    else {
				    //Toast.makeText(getApplicationContext(), "I AM ERROR", Toast.LENGTH_SHORT).show();
                    //toastExceptionObject(e);
			    }
		    }
	    });
    }
}