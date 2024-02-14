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

import javax.security.auth.callback.Callback;

import okhttp3.OkHttpClient;

public class MenuServeur extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serveur);
/*
        try {
            listeClasses();
        } catch (IOException e) {
            e.printStackTrace();
        }

 */
    }
/*
    public void listeClasses() throws IOException {

        OkHttpClient client = new OkHttpClient();
        ArrayList arrayListNomClasses = new ArrayList<String>();

        Request request = new Request.Builder()
                .url("http://da5/Tp_AppEtudiant/EtudiantScript/controleurs/lesClasses.php")
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
                        arrayListNomClasses.add(jsonClasse.getString("nomClasse"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ListView listViewClasses = findViewById(R.id.listViewClasses);

                ArrayAdapter<String> arrayAdapterClasses = new ArrayAdapter<String>(MenuClasses.this, android.R.layout.simple_list_item_1, arrayListNomClasses);

                listViewClasses.setAdapter(arrayAdapterClasses);

            }

            public void onFailure(Call call, IOException e) {
                Log.d("Test", "erreur!!! connexion impossible");
            }

        });


    }

    JSONArray finalJsonArrayClasses = jsonArrayClasses;

    listViewClasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            JSONObject uneClasse = finalJsonArrayClasses.getJSONObject(position);
            Intent intent = new Intent(ClassesActivity.this, EtudiantsUneClasseActivity.class);
            intent.putExtra("uneClasse" , uneClasse.toString());
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }});*/
}
