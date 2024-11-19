package com.example.lamphitryon;
        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.ListView;

        import androidx.appcompat.app.AppCompatActivity;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.IOException;
        import java.text.ParseException;
        import java.text.ParsePosition;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.Locale;

        import okhttp3.Call;
        import okhttp3.Callback;
        import okhttp3.OkHttpClient;
        import okhttp3.Request;
        import okhttp3.Response;

public class LeServiceParDates extends AppCompatActivity {
    private ArrayList<ProposerPlat> arrayListServices;
    private ListView listViewServices;
    private int idService;
    private String date_service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tout_les_services_par_dates);

        arrayListServices = new ArrayList<>();
        listViewServices = findViewById(R.id.listViewServices);
        idService = getIntent().getIntExtra("IDSERVICE", -1);

        try {
            loadServices();
            listViewServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ProposerPlat service = arrayListServices.get(position);

                    String dateStr = String.valueOf(service.getDateService());
                    SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

                    Date date = inputFormat.parse(dateStr, new ParsePosition(0));

                    date_service = (date != null) ? outputFormat.format(date) : "Failed to parse date";

                    Intent intent = new Intent(LeServiceParDates.this, LeServiceActivity.class);
                    intent.putExtra("IDSERVICE", idService);
                    intent.putExtra("DATE_SERVICE", date_service);
                    startActivity(intent);
                }
            });


            Button buttonQuitter = findViewById(R.id.buttonQuitterServicesActivity);
            buttonQuitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LeServiceParDates.this, ServicesActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void loadServices() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://192.168.1.144/projet_perso/Amphitryon_application_android/php/controleurs/afficherLesServicesParDate.php")
                .build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                try {
                    JSONArray jsonArrayServices = new JSONArray(responseStr);
                    arrayListServices.clear();
                    for (int i = 0; i < jsonArrayServices.length(); i++) {
                        JSONObject jsonService = jsonArrayServices.getJSONObject(i);
                        String dateServiceStr = jsonService.getString("DATE_SERVICE");
                        Date dateService = parseDate(dateServiceStr);
                        arrayListServices.add(new ProposerPlat(idService, 0, dateService, 0, 0.0, 0));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CustomServicesAdapter adapter = new CustomServicesAdapter(LeServiceParDates.this, R.layout.custom_service_adapter, arrayListServices);
                            listViewServices.setAdapter(adapter);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("ServicesActivity", "Error loading services", e);
            }
        });
    }

    private Date parseDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}