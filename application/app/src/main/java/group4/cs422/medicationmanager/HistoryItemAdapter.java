package group4.cs422.medicationmanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class HistoryItemAdapter extends BaseAdapter {


    private Context mContext;
    private ArrayList<MedicationTransaction> mTransactions;

    public HistoryItemAdapter(Context context, ArrayList<MedicationTransaction> transactions) {
        mContext = context;
        mTransactions = transactions;
    }

    @Override
    public int getCount() {
        return mTransactions.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("HistAdapter", "Point 1");
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.historylist_row, null, true);
        }

        if (position % 2 == 1)
            view.setBackgroundColor(mContext.getColor(R.color.listOdd));

        else
            view.setBackgroundColor(mContext.getColor(R.color.listEven));


        TextView nameTextView = (TextView)view.findViewById(R.id.takenInformation);
        nameTextView.setText(mTransactions.get(position).getMedName());

        TextView timeTextView = (TextView)view.findViewById(R.id.takenTime);
        timeTextView.setText(mTransactions.get(position).getTimeTaken());

        return view;


    }
}
