package jaguar.com.patientcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by trentrand on 4/17/15.
 */
public class PatientListAdapter extends ArrayAdapter<ParseObject> {
    private Context context;
    private ParseUser patient;
    private List<ParseObject> patients;
    private TextView txtFullname;
    private TextView txtSymptom;

    public PatientListAdapter(Context context, List<ParseObject> patients) {
        super(context, R.layout.simplerow, patients);
        this.context = context;
        this.patients = patients;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.simplerow, parent, false);

        }

        txtFullname = (TextView) convertView.findViewById(R.id.lblFullname);
        txtSymptom = (TextView) convertView.findViewById(R.id.lblSymptom);
        patient = (ParseUser)patients.get(position).get("user");
        try {
            txtFullname.setText(patient.fetchIfNeeded().getString("firstName") + " " + patient.fetchIfNeeded().getString("lastName"));
            txtSymptom.setText(patient.fetchIfNeeded().getString("highestSymptom"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
