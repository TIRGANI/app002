package adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.testappv00.R;

public class CustomAdapter extends ArrayAdapter<String> {
    Context context;
    String[] names;
    int[] images;

    public CustomAdapter(@NonNull Context context, String[] names, int[] images) {
        super(context, R.layout.activity_spinner_item,names);
        this.context = context;
        this.names = names;
        this.images = images;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row  = inflater.inflate(R.layout.activity_spinner_item,null);
            TextView t1 = (TextView) row.findViewById(R.id.sptext);
            ImageView img =(ImageView) row.findViewById(R.id.spimage);

            t1.setText(names[position]);
            img.setImageResource(images[position]);

        return row;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row  = inflater.inflate(R.layout.activity_spinner_item,null);
        TextView t1 = (TextView) row.findViewById(R.id.sptext);
        ImageView img =(ImageView) row.findViewById(R.id.spimage);

        t1.setText(names[position]);
        img.setImageResource(images[position]);

        return row;
    }
}
