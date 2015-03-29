package jaguar.com.patientcare;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.*;

public class LoginActivity extends ActionBarActivity {

    // Pointer to UI elements
    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnLogin;
    private Button btnForgotPassword;

    // Holds copy of text from EditText fields
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize pointer to UI elements
        txtEmail = (EditText) findViewById(R.id.txtemail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnForgotPassword = (Button) findViewById(R.id.btnForgotPassword);

        // Declare UI element event handlers
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogin();
            }
        });

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnForgotPassword();
            }
        });

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "VewvxmlYofAlQkLcRUZjjfJSVf0U9WKeWpWcqfwJ", "K8hbr0l4agNpyJKvRvhmjEBSgkPcvTNrqTVYmxz8");
        ParseAnalytics.trackAppOpened(getIntent());

        // See if we can skip the login view by checking for a cached user session
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // TODO switch to next screen
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

    /**
     * Called when btnLogin is clicked on activity_login.
     * Performs error checking before calling attemptStandardLogin method.
     */
    public void btnLogin() {
        // Retrieve text from EditText fields
        email = (String) txtEmail.getText().toString();
        password = (String) txtPassword.getText().toString();

        // Check if both credentials are entered. Error check
        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter your Email", Toast.LENGTH_LONG).show();
            // TODO can we highlight/shake the EditText we are referring to?
            //End the method early before we attempt standard login
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter your Password", Toast.LENGTH_LONG).show();
            // TODO can we highlight/shake the EditText we are referring to?
            //End the method early before we attempt standard login
            return;
        }

        // If email and password are not blank, attempt to login user
        attemptStandardLogin(email, password);
    }

    /**
     * Validates credential parameters by communicating with Parse database backend.
     * The term "standard" refers to a typical Email / Password credential combination,
     * as opposed to an optional social media login.
     * @param email The email used for login attempt
     * @param password The password used for login attempt
     */
    private void attemptStandardLogin(String email, String password) {
        ParseUser.logInInBackground(email, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // User is logged in successfully
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                    // TODO switch to next screen
                } else {
                    // Login failed. Details found in <e: ParseException>
                    // TODO make a displayErrorMessage(Exception e) method - pass, format and Toast the e.getLocalizedMessage()
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Called when btnForgotPassword is clicked on activity_login.
     * Performs error checking before calling sendPasswordRecovery method.
     */
    public void btnForgotPassword() {
        // Retrieve text from EditText fields
        email = (String) txtEmail.getText().toString();

        // Check if an email address was entered. Error check
        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter your Email for password recovery", Toast.LENGTH_LONG).show();
            // TODO can we highlight/shake the EditText we are referring to?
            //End the method early before we attempt standard login
            return;
        }
        // If the Email field is not blank, send the password recovery email
        sendPasswordRecovery(email);
    }

    /**
     * Sends a password recovery email to the specified email address.
     * @param recoveryEmail The email address to send password recovery email
     */
    private void sendPasswordRecovery(final String recoveryEmail) {
        ParseUser.requestPasswordResetInBackground(recoveryEmail,
                new RequestPasswordResetCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            // An email was successfully sent with reset instructions.
                            Toast.makeText(getApplicationContext(), "Password recovery email has been sent to " + recoveryEmail, Toast.LENGTH_LONG).show();
                            // Disable btnForgotPassword so you can't spam email
                            // TODO implement timer so button is re-enabled, just in case they sent to wrong email or something
                            btnForgotPassword.setEnabled(false);
                        } else {
                            // Password recovery failed. Details found in <e: ParseException>
                            // TODO make a displayErrorMessage(Exception e) method - pass, format and Toast the e.getLocalizedMessage()
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
