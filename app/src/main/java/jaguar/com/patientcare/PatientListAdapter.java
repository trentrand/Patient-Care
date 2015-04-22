package jaguar.com.patientcare;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trentrand on 4/17/15.
 */
public class PatientListAdapter extends ArrayAdapter<ParseUser> {
    private Context context;
    private List<ParseUser> patients;
    private TextView txtFullname;
    private TextView txtSymptom;

    public PatientListAdapter(Context context, List<ParseUser> patientList)
    {
        super(context, R.layout.simplerow, patientList);
        this.context = context;
        this.patients = patientList;
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
        txtFullname.setText(patients.get(position).getString("firstName") + " " + patients.get(position).getString("lastName"));
        txtSymptom.setText(patients.get(position).getString("highestSymptom"));

        return convertView;
    }
}
