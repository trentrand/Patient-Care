package jaguar.com.patientcare;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trentrand on 4/17/15.
 */
public class PatientListAdapter extends ArrayAdapter<ParseUser> {
    private Context context;
    private List<ParseUser> patients;

    public PatientListAdapter(Context context, List<ParseUser> patientList)
    {
        super(context, R.layout.simplerow, patientList);
        this.context = context;
        this.patients = patientList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.simplerow, parent, false);

        TextView txtFullname = (TextView) rowView.findViewById(R.id.lblFullname);
        TextView txtSymptom = (TextView) rowView.findViewById(R.id.lblSymptom);
        System.out.println(patients.get(position).get("firstName") + " " + patients.get(position).get("lastName"));
        txtFullname.setText(patients.get(position).get("firstName") + " " + patients.get(position).get("lastName"));
        txtSymptom.setText("Headache"); //Instead of the same value use position + 1, or something appropriate

        return rowView;
    }
}
