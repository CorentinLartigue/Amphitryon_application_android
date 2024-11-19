package com.example.lamphitryon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AjouterPlatServiceActivity extends AppCompatActivity {
    String responseStr;
    OkHttpClient client = new OkHttpClient();
    private ArrayList<Plat> arrayListPlats;

    private ListView listViewPlatsService;

    private EditText textQuantite;
    private EditText textPrixUnitaire ;

    private boolean platSelectionne = false;
    private int idService;
    private String date_service;

    private ArrayList<Plat> arrayListPlatsService;


    private  int idplat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_plat_service);
        arrayListPlats = new ArrayList<>();
        listViewPlatsService = findViewById(R.id.listViewPlatsService);
        textQuantite = findViewById(R.id.editTextQuantite);
        textPrixUnitaire = findViewById(R.id.editTextPrixUnitaire);
        idService = getIntent().getIntExtra("IDSERVICE", -1);
        date_service = getIntent().getStringExtra("DATE_SERVICE");
        arrayListPlatsService= new ArrayList<>();

        try {

            listePlatsService();

            listViewPlatsService.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Plat plat = arrayListPlats.get(position);
                    idplat = plat.getNumero();
                    platSelectionne=true;
                }
            });

            final Button buttonAjouterPlatService = (Button)findViewById(R.id.buttonAjouterPlatService);
            buttonAjouterPlatService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (platSelectionne && !TextUtils.isEmpty(textQuantite.getText()) && !TextUtils.isEmpty(textPrixUnitaire.getText())) {
                            ajouterPlat();
                        }else{
                            Toast.makeText(AjouterPlatServiceActivity.this, "Veuillez sélectionner un plat et remplir les champs pour l'ajouter", Toast.LENGTH_SHORT).show();
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });

            final Button buttonQuitterAjouterPlatService = (Button)findViewById(R.id.buttonQuitterAjouterPlatService);
            buttonQuitterAjouterPlatService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ajouterPlat() throws IOException {


        RequestBody formBody = new FormBody.Builder()
                .add("IDPLAT", String.valueOf(idplat))
                .add("IDSERVICE", String.valueOf(idService))
                .add("DATE_SERVICE", String.valueOf(date_service))
                .add("QUANTITEPROPOSE", textQuantite.getText().toString())
                .add("QUANTITEVENDUE", "0")
                .add("PRIXVENTE",  textPrixUnitaire.getText().toString())
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.144/projet_perso/Amphitryon_application_android/php/controleurs/ajouterPlatService.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public  void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {
                    Log.d("LeServiceActivity", "Plat ajouté au service dans la base de données");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textQuantite.setText("");
                            textPrixUnitaire.setText("");
                            Toast.makeText(AjouterPlatServiceActivity.this, "Plat ajouté avec succès au service", Toast.LENGTH_SHORT).show();
                        }
                    });

                    listePlatsService();

                } else {
                    Log.d("LeServiceActivity", "Erreur lors de l'ajout du plat au service dans la base de données");
                }
            }

            public void onFailure(Call call, IOException e)
            {
                Log.e("LeServiceActivity", "Erreur de connexion lors de l'ajout du plat au service dans la base de données", e);
            }

        });
        platSelectionne = false;
    }

    public void listePlats(ArrayList<Plat> arrayListPlatsService) throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.1.144/projet_perso/Amphitryon_application_android/php/controleurs/lesPlats.php")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                JSONArray jsonArrayPlats = null;
                try {
                    jsonArrayPlats = new JSONArray(responseStr);

                    arrayListPlats.clear();

                    for (int i = 0; i < jsonArrayPlats.length(); i++) {
                        JSONObject jsonPlat = jsonArrayPlats.getJSONObject(i);
                        Integer numero = jsonPlat.getInt("IDPLAT");
                        String nom = jsonPlat.getString("NOMPLAT");
                        String descriptif = jsonPlat.getString("DESCRIPTIF");
                        Plat plat = new Plat(numero, nom, descriptif);

                        boolean platDejaPresent = false;
                        for (Plat p : arrayListPlatsService) {
                            if (p.getNumero() == plat.getNumero()) {
                                platDejaPresent = true;
                                break;
                            }
                        }
                        if (!platDejaPresent) {
                            arrayListPlats.add(plat);
                        }
                    }
                    platSelectionne = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ListView listViewPlatsService = findViewById(R.id.listViewPlatsService);
                        CustomPlatsAdapter adapter = new CustomPlatsAdapter(AjouterPlatServiceActivity.this, R.layout.custom_plats_adapter, arrayListPlats);
                        listViewPlatsService.setAdapter(adapter);
                    }
                });
            }

            public void onFailure(Call call, IOException e) {
                Log.d("LeServiceActivity", "Erreur lors de la récupération des plats", e);
            }

        });
    }



    public void listePlatsService() throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("IDSERVICE", String.valueOf(idService))
                .add("DATE_SERVICE", String.valueOf(date_service))
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.144/projet_perso/Amphitryon_application_android/php/controleurs/afficherLesPlatsDuService.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                JSONArray jsonArrayPlats = null;
                try {
                    jsonArrayPlats = new JSONArray(responseStr);

                    arrayListPlatsService.clear();

                    for (int i = 0; i < jsonArrayPlats.length(); i++) {
                        JSONObject jsonPlat = jsonArrayPlats.getJSONObject(i);
                        Integer numero = jsonPlat.getInt("IDPLAT");
                        String nom = jsonPlat.getString("NOMPLAT");
                        String descriptif = jsonPlat.getString("DESCRIPTIF");
                        arrayListPlatsService.add(new Plat(numero, nom, descriptif));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            listePlats(arrayListPlatsService);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("LeServiceActivity", "Erreur lors de la récupération des plats du service", e);
            }
        });
    }

    public ArrayList<Plat> convertirJsonEnListePlats(String listePlatsJson) {
        ArrayList<Plat> listePlats = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(listePlatsJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonPlat = jsonArray.getJSONObject(i);
                int idPlat = jsonPlat.getInt("IDPLAT");
                String nomPlat = jsonPlat.getString("NOMPLAT");
                String descriptifPlat = jsonPlat.getString("DESCRIPTIF");
                Plat plat = new Plat(idPlat, nomPlat, descriptifPlat);
                listePlats.add(plat);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listePlats;
    }
}