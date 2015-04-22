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

import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParseClassName;
import com.parse.ParseUser;

@ParseClassName("Symptoms")
public class Symptoms extends ParseObject {

    // Reference to linked User
    private ParseUser referencedUser;

    // Int array of Symptoms rating
    private int[] symptomRatings = new int[9];

    private String[] symptomRatingsLabels = { "Pain", "Fatigue", "Numbness", "Spasticity", "Vision", "Dizziness", "Bladder", "Cognitive", "Emotional"};

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

    // Accessor methods for Highest Symptom level
    // Retrieves and computes String label of the highest symptom
    public String getHighestSymptom() {
        int maxSymptomIndex = 0;
        for (int i = 0; i < symptomRatings.length; i++){
            int symptomRating = symptomRatings[i];
            if ((symptomRating > symptomRatings[maxSymptomIndex])){
                maxSymptomIndex = i;
            }
        }
        return symptomRatingsLabels[maxSymptomIndex].toString();
    }

    // Accessor methods for Symptom count
    // Sets the sum of all symptom ratings
    public void setSymptomCount() {
        // Hold the total temporarily
        int total = 0;

        for (int symptom: symptomRatings) {
            total += (int) symptom;
        }

        put ("symptomCount", total);
    }

    public int getSymptomCount() {
        return getInt("symptomCount");
    }
}
