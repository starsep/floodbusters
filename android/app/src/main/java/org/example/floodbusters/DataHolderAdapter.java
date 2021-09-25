package org.example.floodbusters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.example.floodbusters.dataholder.WarningItemDataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DataHolderAdapter extends ArrayAdapter {

    List<WarningItemDataHolder> items = new ArrayList<>();
    int resourceId;
    Context context;

    public DataHolderAdapter(Context context, int resourceId, @NotNull List<WarningItemDataHolder> list) {
        super(context, resourceId, list);
        this.items = list;
        this.resourceId = resourceId;
        this.context = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(this.resourceId, null);//set layout for displaying items
        /*ImageView icon = (ImageView) view.findViewById(R.id.icon);//get id for image view
        icon.setImageResource(countryFlags[i]);*///set image of the item’s

        TextView shortTextView = (TextView) view.findViewById(R.id.warningShortText);
        shortTextView.setText(this.items.get(i).getWarningShortText());

        TextView longTextView = (TextView) view.findViewById(R.id.warningLongText);
        longTextView.setText(this.items.get(i).getWarningLongText());
        return view;
    }

    @Override
    public int getCount() {
        return items.size();
    }

}
