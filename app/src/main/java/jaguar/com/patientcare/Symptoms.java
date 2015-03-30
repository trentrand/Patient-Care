package jaguar.com.patientcare;

/**
 * Created by trentrand on 3/29/15.
 *
 * Symptoms Included:
 * Pain
 * Fatigue
 * Numbness
 * Spasticity
 * Vision Problems
 * Dizziness
 * Bladder Problems
 * Cognitive Changes
 * Emotional Changes
 */

import com.parse.ParseObject;
import com.parse.ParseClassName;
import com.parse.ParseUser;

@ParseClassName("Symptoms")
public class Symptoms extends ParseObject {

    // Reference to linked User
    private ParseUser referencedUser;

    // Int array of Symptoms rating
    private int[] symptomRatings = new int[9];

    // Accessor methods for associated User
    public ParseUser getUser() {
        return getParseUser("user");
    }

    public void setUser(ParseUser user) {
        put ("user", user);
        referencedUser = user;
    }

    // Accessor methods for Pain level
    public int getPainLevel() {
        return getInt("painLevel");
    }

    public void setPainLevel(int painLevel) {
        put ("painLevel", painLevel);
        symptomRatings[0] = painLevel;
    }

    // Accessor methods for Fatigue level
    public int getFatigueLevel() {
        return getInt("fatigueLevel");
    }

    public void setFatigueLevel(int fatigueLevel) {
        put ("fatigueLevel", fatigueLevel);
        symptomRatings[1] = fatigueLevel;
    }
    // Accessor methods for Numbness level
    public int getNumbnessLevel() {
        return getInt("numbnessLevel");
    }

    public void setNumbnessLevel(int numbnessLevel) {
        put ("numbnessLevel", numbnessLevel);
        symptomRatings[2] = numbnessLevel;
    }
    // Accessor methods for Spasticity level
    public int getSpasticityLevel() {
        return getInt("spasticityLevel");
    }

    public void setSpasticityLevel(int spasticityLevel) {
        put ("spasticityLevel", spasticityLevel);
        symptomRatings[3] = spasticityLevel;
    }
    // Accessor methods for Vision Problem level
    public int getVisionLevel() {
        return getInt("visionLevel");
    }

    public void setVisionLevel(int visionLevel) {
        put ("visionLevel", visionLevel);
        symptomRatings[4] = visionLevel;
    }
    // Accessor methods for Dizziness level
    public int getDizzinessLevel() {
        return getInt("dizzinessLevel");
    }

    public void setDizzinessLevel(int dizzinessLevel) {
        put ("dizzinessLevel", dizzinessLevel);
        symptomRatings[5] = dizzinessLevel;
    }
    // Accessor methods for Bladder level
    public int getBladderLevel() {
        return getInt("bladderLevel");
    }

    public void setBladderLevel(int bladderLevel) {
        put ("bladderLevel", bladderLevel);
        symptomRatings[6] = bladderLevel;
    }
    // Accessor methods for Cognitive level
    public int getCognitiveLevel() {
        return getInt("cognitiveLevel");
    }

    public void setCognitiveLevel(int cognitiveLevel) {
        put ("cognitiveLevel", cognitiveLevel);
        symptomRatings[7] = cognitiveLevel;
    }
    // Accessor methods for Emotional level
    public int getEmotionalLevel() {
        return getInt("emotionalLevel");
    }

    public void setEmotionalLevel(int emotionalLevel) {
        put ("emotionalLevel", emotionalLevel);
        symptomRatings[8] = emotionalLevel;
    }



}
