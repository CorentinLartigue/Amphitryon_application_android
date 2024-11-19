package com.example.lamphitryon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomPlatsAdapter extends ArrayAdapter<Plat> {
    private Context mContext;
    private int mResource;

    public CustomPlatsAdapter(Context context, int resource, ArrayList<Plat> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        if (convertView == null) {
            convertView = inflater.inflate(mResource, parent, false);
        }

        TextView numeroTextView = convertView.findViewById(R.id.numeroTextView);
        TextView nomTextView = convertView.findViewById(R.id.nomTextView);
        TextView descriptifTextView = convertView.findViewById(R.id.descriptifTextView);

        Plat plat = getItem(position);

        if (plat != null) {
            numeroTextView.setText("N°" + String.valueOf(plat.getNumero()));
            nomTextView.setText(plat.getNom());
            descriptifTextView.setText(plat.getDescriptif());
        }

        return convertView;
    }
}

