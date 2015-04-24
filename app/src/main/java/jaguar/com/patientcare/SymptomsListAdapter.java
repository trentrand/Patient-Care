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
        String full = symptoms.get(position).getUpdatedAt().toString();
        String date = full.substring(0, full.indexOf(":") - 3);
        String hour = full.substring(full.indexOf(":") - 2, full.indexOf(":"));
        String mins = full.substring(full.indexOf(":") + 1, full.indexOf(":") + 3);
        String am_pm = "am";
        int h = Integer.parseInt(hour);
        if((h-12) >= 0){
            h = h-12;
            am_pm = "pm";
        }
        String readableDate = date + " - " + h + ":" + mins + am_pm;
        txtDate.setText(readableDate);

        return convertView;
    }

}
