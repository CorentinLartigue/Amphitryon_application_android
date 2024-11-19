package com.example.lamphitryon;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MenuServeur extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serveur);

        try {
            listeClasses();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void listeClasses() throws IOException {

        OkHttpClient client = new OkHttpClient();
        ArrayList arrayListPlats = new ArrayList<String>();

        Request request = new Request.Builder()
                .url("http://192.168.1.144/projet_perso/Amphitryon_application_android/php/controleurs/commande.php")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                JSONArray jsonArrayClasses = null;
                try {
                    jsonArrayClasses = new JSONArray(responseStr);

                    for (int i = 0; i < jsonArrayClasses.length(); i++) {
                        JSONObject jsonClasse = null;
                        jsonClasse = jsonArrayClasses.getJSONObject(i);
                        arrayListPlats.add(jsonClasse.getString("nomClasse"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ListView listViewClasses = findViewById(R.id.listViewPlats);

                ArrayAdapter<String> arrayAdapterClasses = new ArrayAdapter<String>(MenuServeur.this, android.R.layout.simple_list_item_1, arrayListPlats);

                listViewClasses.setAdapter(arrayAdapterClasses);


                JSONArray finalJsonArrayClasses = jsonArrayClasses;


            }

            public void onFailure(Call call, IOException e) {
                Log.d("Test", "erreur!!! connexion impossible");
            }

        });


    }
}
