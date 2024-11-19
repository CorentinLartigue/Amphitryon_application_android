package com.example.lamphitryon;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CustomServicesParDatesAdapter extends ArrayAdapter<ProposerPlat> {

    private Context mContext;

    public CustomServicesParDatesAdapter(Context context, int custom_service_adapter, ArrayList<ProposerPlat> services) {
            super(context, 0, services);
            mContext = context;
            }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textViewServiceInfo = (TextView) convertView;
        if (textViewServiceInfo == null) {
            textViewServiceInfo = new TextView(mContext);
            textViewServiceInfo.setPadding(0, 16, 0, 16);
            textViewServiceInfo.setTextSize(18);
            textViewServiceInfo.setTextColor(ContextCompat.getColor(mContext, android.R.color.black));
        }

        ProposerPlat service = getItem(position);

        if (service != null) {
            String serviceInfo = "Service numéro " + service.getIdService();
            textViewServiceInfo.setText(serviceInfo);
        }

        return textViewServiceInfo;
    }


}