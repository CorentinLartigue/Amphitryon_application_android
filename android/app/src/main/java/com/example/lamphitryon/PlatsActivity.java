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

public class PlatsActivity extends AppCompatActivity {
    private ArrayList<Plat> arrayListPlats;
    private ListView listViewPlats;

    private static final int REQUEST_CODE_AJOUT_PLAT = 1;

    private static final int REQUEST_CODE_MODIFIER_PLAT = 2;

    private boolean platSelectionne = false;

    private  int idplat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tout_les_plats);


        arrayListPlats = new ArrayList<>();
        listViewPlats = findViewById(R.id.listViewPlats);
        try {
            listePlats();

            listViewPlats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    platSelectionne = true;
                    Plat plat = arrayListPlats.get(position);

                    idplat = plat.getNumero();

                }
            });

            final Button buttonModifierPlat = findViewById(R.id.ModifierPlat);
            buttonModifierPlat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (platSelectionne) {
                        Intent intent = new Intent(PlatsActivity.this, ModifierPlatActivity.class);
                        intent.putExtra("IDPLAT", String.valueOf(idplat));
                        startActivityForResult(intent, REQUEST_CODE_MODIFIER_PLAT);

                    } else {
                        Toast.makeText(PlatsActivity.this, "Veuillez sélectionner un plat avant de modifier", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            final Button btnAjouterPlat = findViewById(R.id.AjouterPlat);
            btnAjouterPlat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PlatsActivity.this, AjouterPlatActivity.class);
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
                            Plat plat = arrayListPlats.get(position);
                            Integer numeroPlat = plat.getNumero();
                            Log.d("numeroPlat", "onClick: " + numeroPlat);
                            arrayListPlats.remove(position);
                            ((BaseAdapter) listViewPlats.getAdapter()).notifyDataSetChanged();
                            supprimerPlatEnBDD(numeroPlat);
                            platSelectionne = false;
                        } else {
                            Toast.makeText(PlatsActivity.this, "Veuillez sélectionner un plat à supprimer", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PlatsActivity.this, "Veuillez sélectionner un plat avant de supprimer", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            final Button buttonQuitterPlatsActivity = (Button) findViewById(R.id.buttonQuitterPlatsActivity);
            buttonQuitterPlatsActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PlatsActivity.this, MenuChefCuisinier.class);
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
                    listePlats();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == REQUEST_CODE_MODIFIER_PLAT) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    listePlats();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void listePlats() throws IOException {
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
                        arrayListPlats.add(new Plat(numero, nom, descriptif));
                    }
                    platSelectionne = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ListView listViewPlats = findViewById(R.id.listViewPlats);
                        CustomPlatsAdapter adapter = new CustomPlatsAdapter(PlatsActivity.this, R.layout.custom_plats_adapter, arrayListPlats);
                        listViewPlats.setAdapter(adapter);
                    }
                });
            }

            public void onFailure(Call call, IOException e) {
                Log.d("PlatsActivity", "Erreur lors de la récupération des plats", e);
            }

        });
    }


    private void supprimerPlatEnBDD(Integer numeroPlat) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("IDPLAT", numeroPlat.toString())
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.1.144/projet_perso/Amphitryon_application_android/php/controleurs/supprimerPlat.php")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.d("PlatsActivity", "Plat supprimé de la base de données");
                } else {
                    Log.d("PlatsActivity", "Erreur lors de la suppression du plat de la base de données");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("PlatsActivity", "Erreur de connexion lors de la suppression du plat de la base de données", e);
            }
        });
    }


}
