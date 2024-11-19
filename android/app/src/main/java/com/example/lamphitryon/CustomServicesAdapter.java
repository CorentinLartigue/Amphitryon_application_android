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

public class CustomServicesAdapter extends ArrayAdapter<ProposerPlat> {

    private Context mContext;

    public CustomServicesAdapter(Context context, int custom_service_adapter, ArrayList<ProposerPlat> services) {
            super(context, 0, services);
            mContext = context;
            }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textViewServiceInfo = (TextView) convertView;
        if (textViewServiceInfo == null) {
            textViewServiceInfo = new TextView(mContext);
            textViewServiceInfo.setPadding(0, 16, 0, 16); // Ajouter plus d'espace autour du texte
            textViewServiceInfo.setTextSize(18); // Ajuster la tail du texte
            textViewServiceInfo.setTextColor(ContextCompat.getColor(mContext, android.R.color.black)); // Définir la couleur du texte
        }

        ProposerPlat service = getItem(position);

        if (service != null) {
            String serviceInfo = "Service numéro " + service.getIdService() + " du " + formatDate(service.getDateService());
            textViewServiceInfo.setText(serviceInfo);
        }

        return textViewServiceInfo;
    }

    private String formatDate(Date date) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            return format.format(date);
    }
}