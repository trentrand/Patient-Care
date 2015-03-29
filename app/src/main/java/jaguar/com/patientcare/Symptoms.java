package jaguar.com.patientcare;

/**
 * Created by trentrand on 3/29/15.
 */

import com.parse.ParseObject;
import com.parse.ParseClassName;
import com.parse.ParseUser;

@ParseClassName("Symptoms")
public class Symptoms extends ParseObject {

    // Accessor methods for associated User
    public ParseUser getUser() {
        return getParseUser("user");
    }

    public void setUser(ParseUser user) {
        put ("user", user);
    }

    // Accessor methods for Pain level
    public int getPainLevel() {
        return getInt("painLevel");
    }

    public void setPainLevel(int painLevel) {
        put ("painLevel", painLevel);
    }

    
}
