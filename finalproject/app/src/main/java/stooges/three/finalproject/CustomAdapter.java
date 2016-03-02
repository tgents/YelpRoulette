package stooges.three.finalproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by iguest on 3/1/16.
 */
public class CustomAdapter extends ArrayAdapter {
    // View lookup cache
    private static class ViewHolder {
        TextView text;
        Spinner options;
    }

    public CustomAdapter(Context context, ArrayList<String> spinneritems) {
        super(context, R.layout.customlistitem);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.customlistitem, parent, false);
            viewHolder.text = (TextView) convertView.findViewById(R.id.edittext);
            viewHolder.options = (Spinner) convertView.findViewById(R.id.spinner);
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.distance, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            viewHolder.options.setAdapter(adapter);
            viewHolder.options.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.v("Hellomoto", "Clicked on spinner in listview");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.text.setText("");
        //viewHolder.options.setText(user.hometown);
        // Return the completed view to render on screen
        return convertView;
    }

}
