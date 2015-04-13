package jaguar.com.patientcare;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseUser;

/**
 * Created by graemedrucker on 4/13/15.
 */
public class RegisterPatientActivity extends ActionBarActivity{
    private String email;
    private String password;
    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnRegister;
    private ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_patient);
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
	    //button stuff
    }
    
    public void registerPatient(String email, String password){
	    //registering stuff
    }
}