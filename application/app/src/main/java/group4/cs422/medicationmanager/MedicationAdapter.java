package group4.cs422.medicationmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MedicationAdapter extends ArrayAdapter<Medication> {

    private Context context;
    private ArrayList<Medication> medicationList;

    // Constructor
    public MedicationAdapter(Context context, int resource, ArrayList<Medication> list){
        super(context, resource, list);
        this.context = context;
        this.medicationList = list;
    }


    // Must override getView() method
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);

        Medication currentMed = medicationList.get(position);

        // List item stuff
        TextView text = (TextView) listItem.findViewById(R.id.med_list_textview);
        if (position % 2 == 1)
            text.setBackgroundColor(context.getColor(R.color.listOdd));
        else
            text.setBackgroundColor(context.getColor(R.color.listEven));


        text.setText(currentMed.getMedName());
        text.setTag(R.id.medNameID,currentMed.getMedName());
        text.setTag(R.id.doseAmountID,currentMed.getDoseAmount());
        text.setTag(R.id.hourlyFreqID,currentMed.getHourlyFrequency());
        text.setTag(R.id.daysByDoseID,currentMed.getDaysBetweenDose());

        return listItem;
    }
}
