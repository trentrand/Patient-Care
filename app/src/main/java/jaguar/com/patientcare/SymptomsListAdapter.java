package jaguar.com.patientcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by graemedrucker on 4/23/15.
 */
public class SymptomsListAdapter extends ArrayAdapter<ParseObject>{
    private Context context;
    private List<ParseObject> symptoms;
    private TextView txtDate;

    public SymptomsListAdapter(Context context, List<ParseObject> symptomsList) {
        super(context, R.layout.simplerow, symptomsList);
        this.context = context;
        this.symptoms = symptomsList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.simplerow, parent, false);
        }

        txtDate = (TextView) convertView.findViewById(R.id.lblFullname);
        txtDate.setText(symptoms.get(position).getUpdatedAt().toString());

        return convertView;
    }

}
