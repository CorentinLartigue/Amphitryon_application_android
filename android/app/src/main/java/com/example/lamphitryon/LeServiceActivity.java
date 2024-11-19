package com.example.lamphitryon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
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

public class LeServiceActivity extends AppCompatActivity {
    private ArrayList<PlatWithQuantities> arrayListPlatsService;
    private ListView listViewPlats;

    private static final int REQUEST_CODE_AJOUT_PLAT = 1;
    private static final int REQUEST_CODE_MODIFIER_PLAT = 2;
    private boolean platSelectionne = false;
    private int idplat;
    private int idService;
    private String date_service;
    private String quantiteProposee;
    private String quantiteVendue;
    private String prixVente;
    private String nomPlat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_le_service);

        arrayListPlatsService = new ArrayList<>();
        listViewPlats = findViewById(R.id.listViewPlats);

        idService = getIntent().getIntExtra("IDSERVICE", -1);
        date_service = getIntent().getStringExtra("DATE_SERVICE");

        try {
            loadPlats(idService,date_service);

            listViewPlats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    platSelectionne = true;
                    PlatWithQuantities plat = arrayListPlatsService.get(position);
                    idplat = plat.getPlat().getNumero();
                    quantiteProposee = plat.getQuantiteProposee();
                    quantiteVendue = plat.getQuantiteVendue();
                    prixVente = plat.getPrixVente();
                    nomPlat = plat.getPlat().getNom();


                }
            });

            final Button buttonModifierPlat = findViewById(R.id.ModifierPlat);
            buttonModifierPlat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (platSelectionne) {
                        Intent intent = new Intent(LeServiceActivity.this, ModifierPlatServiceActivity.class);
                        intent.putExtra("IDPLAT", String.valueOf(idplat));
                        intent.putExtra("IDSERVICE", String.valueOf(idService));
                        intent.putExtra("DATE_SERVICE", date_service);
                        intent.putExtra("QUANTITEPROPOSEE", quantiteProposee);
                        intent.putExtra("QUANTITEVENDUE", quantiteVendue);
                        intent.putExtra("PRIXVENTE", prixVente);
                        intent.putExtra("NOMPLAT", nomPlat);
                        startActivityForResult(intent, REQUEST_CODE_MODIFIER_PLAT);
                    } else {
                        Toast.makeText(LeServiceActivity.this, "Veuillez sélectionner un plat avant de modifier sa quantité et son prix", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            final Button btnAjouterPlat = findViewById(R.id.AjouterPlat);
            btnAjouterPlat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LeServiceActivity.this, AjouterPlatServiceActivity.class);
                    intent.putExtra("IDSERVICE", idService);
                    intent.putExtra("DATE_SERVICE", date_service);
                    startActivityForResult(intent, REQUEST_CODE_AJOUT_PLAT);
                }
            });

            final Button buttonSupprimerPlat = findViewById(R.id.SupprimerPlat);
            buttonSupprimerPlat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (platSelectionne) {
                        int position = listViewPlats.getCheckedItemPosition();
                        if (position != ListView.INVALID_POSITION) {
                            PlatWithQuantities plat = arrayListPlatsService.get(position);
                            Integer numeroPlat = plat.getPlat().getNumero();
                            arrayListPlatsService.remove(position);
                            ((BaseAdapter) listViewPlats.getAdapter()).notifyDataSetChanged();
                            supprimerPlatService(numeroPlat,idService,date_service);
                            platSelectionne = false;
                        } else {
                            Toast.makeText(LeServiceActivity.this, "Veuillez sélectionner un plat à supprimer", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LeServiceActivity.this, "Veuillez sélectionner un plat avant de supprimer", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            final Button buttonQuitterLeServiceActivity = findViewById(R.id.buttonQuitterLeServiceActivity);
            buttonQuitterLeServiceActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LeServiceActivity.this, LeServiceParDates.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        platSelectionne = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_AJOUT_PLAT) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    loadPlats(idService,date_service);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == REQUEST_CODE_MODIFIER_PLAT) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    loadPlats(idService,date_service);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadPlats(int idService,String date_service) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("IDSERVICE",String.valueOf(idService))
                .add("DATE_SERVICE",String.valueOf(date_service))
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
                Log.d( "Resultat requete: ", responseStr);
                JSONArray jsonArrayPlats = null;
                try {
                    jsonArrayPlats = new JSONArray(responseStr);

                    arrayListPlatsService.clear();

                    for (int i = 0; i < jsonArrayPlats.length(); i++) {
                        JSONObject jsonPlat = jsonArrayPlats.getJSONObject(i);
                        Integer numero = jsonPlat.getInt("IDPLAT");
                        String nom = jsonPlat.getString("NOMPLAT");
                        String descriptif = jsonPlat.getString("DESCRIPTIF");
                        String quantiteProposee = jsonPlat.getString("QUANTITEPROPOSEE");
                        String quantiteVendue = jsonPlat.getString("QUANTITEVENDUE");
                        String prixVente = jsonPlat.getString("PRIXVENTE");
                        Plat plat = new Plat(numero, nom, descriptif);
                        PlatWithQuantities platWithQuantities = new PlatWithQuantities(plat, quantiteProposee, quantiteVendue, prixVente);
                        arrayListPlatsService.add(platWithQuantities);
                    }
                    Log.d( "Resultat requete: ", String.valueOf(arrayListPlatsService));

                    platSelectionne = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PlatWithQuantitiesAdapter adapter = new PlatWithQuantitiesAdapter(LeServiceActivity.this, R.layout.custom_plats_with_quantities_adapter, arrayListPlatsService);
                        listViewPlats.setAdapter(adapter);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("LeServiceActivity", "Erreur lors de la récupération des plats du service", e);
            }
        });
    }

    private void supprimerPlatService(Integer numeroPlat,int idService,String date_service) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("IDPLAT", numeroPlat.toString())
                .add("IDSERVICE",String.valueOf(idService))
                .add("DATE_SERVICE",String.valueOf(date_service))
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.1.144/projet_perso/Amphitryon_application_android/php/controleurs/supprimerLesPlatsDuService.php")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.d("LeServiceActivity", "Plat du service supprimé de la base de données");
                } else {
                    Log.d("LeServiceActivity", "Erreur lors de la suppression du plat du service dans la base de données");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("LeServiceActivity", "Erreur de connexion lors de la suppression du plat du service dans la base de données", e);
            }
        });
    }

}
