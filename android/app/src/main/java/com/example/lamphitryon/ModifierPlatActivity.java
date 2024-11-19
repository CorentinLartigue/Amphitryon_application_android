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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ModifierPlatActivity extends AppCompatActivity {

    private int idPlat;
    private Spinner spinnerCategories;
    private List<Categorie> categoriesList;
    private EditText textNomPlat;
    private EditText textDescriptionPlat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_plat);

        textNomPlat = findViewById(R.id.editTextNomPlat);
        textDescriptionPlat = findViewById(R.id.editTextDescriptionPlat);
        spinnerCategories = findViewById(R.id.CategoriesLePlat);

        idPlat = Integer.valueOf(getIntent().getStringExtra("IDPLAT"));

        Log.d( "onCreate: ",String.valueOf(idPlat));
        chargerInformationsPlat(idPlat);


        Button buttonModifierPlat = findViewById(R.id.ModifierPlat);
        buttonModifierPlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NomPlat = textNomPlat.getText().toString();
                String descriptionPlat = textDescriptionPlat.getText().toString();
                int idCateg = ((Categorie) spinnerCategories.getSelectedItem()).getNumero();
                if (!TextUtils.isEmpty(textNomPlat.getText()) && !TextUtils.isEmpty(textDescriptionPlat.getText())) {
                    modifierPlat(idPlat, descriptionPlat, NomPlat, idCateg);
                }else{
                    Toast.makeText(ModifierPlatActivity.this, "Veuillez remplir les champs pour modifier le plat", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button buttonQuitterModifierPlat = findViewById(R.id.buttonQuitterModifierPlat);
        buttonQuitterModifierPlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();            }
        });
    }

    private void chargerInformationsPlat(int idPlat) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("IDPLAT", String.valueOf(idPlat))
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.144/projet_perso/Amphitryon_application_android/php/controleurs/lePlat.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                Log.d("onResponse: ", responseStr);
                try {
                    final JSONObject jsonInfoPlat = new JSONObject(responseStr);
                    final String nom = jsonInfoPlat.getString("NOMPLAT");
                    final String descriptif = jsonInfoPlat.getString("DESCRIPTIF");
                    final int categorieId = jsonInfoPlat.getInt("IDCATEG");

                    Log.d("Plat Info", "Nom: " + nom + ", Descriptif: " + descriptif + ", Categorie ID: " + categorieId);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textNomPlat.setText(nom);
                            textDescriptionPlat.setText(descriptif);
                            Log.d("Plat Info", "Champs de texte mis à jour avec succès.");

                            listeCategorie(categorieId);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call call, IOException e) {
                Log.e("ModifierPlatActivity", "Erreur de connexion lors de la modification du plat à la base de données", e);
            }
        });
    }

    public void listeCategorie(final int categorieId) {
        categoriesList = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.1.144/projet_perso/Amphitryon_application_android/php/controleurs/lesCategories.php")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                JSONArray jsonArrayCategories = null;
                try {
                    jsonArrayCategories = new JSONArray(responseStr);

                    for (int i = 0; i < jsonArrayCategories.length(); i++) {
                        JSONObject jsonCategorie = jsonArrayCategories.getJSONObject(i);
                        Integer numero = jsonCategorie.getInt("IDCATEG");
                        String nom = jsonCategorie.getString("NOMCATEG");
                        categoriesList.add(new Categorie(numero, nom));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CategorieAdapter adapter = new CategorieAdapter(ModifierPlatActivity.this, categoriesList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCategories.setAdapter(adapter);

                        for (int i = 0; i < categoriesList.size(); i++) {
                            if (categoriesList.get(i).getNumero() == categorieId) {
                                spinnerCategories.setSelection(i);
                                break;
                            }
                        }

                        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                                Categorie selectedCategory = (Categorie) adapterView.getItemAtPosition(position);
                                int categorieId = selectedCategory.getNumero();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
                    }
                });
            }

            public void onFailure(Call call, IOException e) {
                Log.d("Test", "erreur!!! connexion impossible");
            }
        });
    }

    private void modifierPlat(int idPlat,String descriptionPlat,String NomPlat,int idCateg) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("IDPLAT", String.valueOf(idPlat))
                .add("IDCATEG",String.valueOf(idCateg))
                .add("NOMPLAT",NomPlat )
                .add("DESCRIPTIF", descriptionPlat )
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.144/projet_perso/Amphitryon_application_android/php/controleurs/modifierPlat.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            public  void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {
                    Log.d("ModifierPlatActivity", "Plat modifié en base de données");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ModifierPlatActivity.this, "Plat modifié avec succès", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Log.d("ModifierPlatActivity", "Erreur lors de la modification du plat en base de données");
                }
            }

            public void onFailure(Call call, IOException e)
            {
                Log.e("ModifierPlatActivity", "Erreur de connexion lors de la modification du plat à la base de données", e);
            }

        });
    }
}
