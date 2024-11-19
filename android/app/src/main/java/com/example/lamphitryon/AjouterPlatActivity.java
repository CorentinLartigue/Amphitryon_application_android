package com.example.lamphitryon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

public class AjouterPlatActivity extends AppCompatActivity {
    String responseStr;
    OkHttpClient client = new OkHttpClient();
    private Spinner spinnerCategories;
    private List<Categorie> categoriesList;
    private EditText textDescriptionPlat;
    private EditText textNomPlat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_plat);

        spinnerCategories = findViewById(R.id.CategoriesPlats);

        categoriesList = new ArrayList<>();
        textDescriptionPlat = findViewById(R.id.editTextDescriptionPlat);
        textNomPlat = findViewById(R.id.editTextNomPlat);


        try {

            listeCategorie();

            final Button CreerPlat = (Button)findViewById(R.id.CreerPlat);
            CreerPlat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (!TextUtils.isEmpty(textDescriptionPlat.getText()) && !TextUtils.isEmpty(textNomPlat.getText())) {
                            ajouterPlat();
                        }else{
                            Toast.makeText(AjouterPlatActivity.this, "Veuillez remplir les champs pour ajouter le plat", Toast.LENGTH_SHORT).show();
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });

            final Button buttonQuitterAjouterPlat = (Button)findViewById(R.id.buttonQuitterAjouterPlat);
            buttonQuitterAjouterPlat.setOnClickListener(new View.OnClickListener() {
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

        final EditText textNomPlat = findViewById(R.id.editTextNomPlat);
        final EditText textDescriptionPlat = findViewById(R.id.editTextDescriptionPlat);
        Categorie selectedCategory = (Categorie) spinnerCategories.getSelectedItem();

        int categorieId = selectedCategory.getNumero();

        RequestBody formBody = new FormBody.Builder()
                .add("NOMPLAT", textNomPlat.getText().toString())
                .add("DESCRIPTIF",  textDescriptionPlat.getText().toString())
                .add("IDCATEG",String.valueOf(categorieId))
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.144/projet_perso/Amphitryon_application_android/php/controleurs/creerPlat.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public  void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {
                    Log.d("AjouterPlatActivity", "Plat ajouté à la base de données");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textNomPlat.setText("");
                            textDescriptionPlat.setText("");
                            Toast.makeText(AjouterPlatActivity.this, "Plat ajouté avec succès", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Log.d("AjouterPlatActivity", "Erreur lors de l'ajout du plat à la base de données");
                }
            }

            public void onFailure(Call call, IOException e)
            {
                Log.e("AjouterPlatActivity", "Erreur de connexion lors de l'ajout du plat à la base de données", e);
            }

        });
    }

    public void listeCategorie() throws IOException {

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
                        CategorieAdapter adapter = new CategorieAdapter(AjouterPlatActivity.this, categoriesList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinnerCategories.setAdapter(adapter);
                    }
                });
                spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        Categorie selectedCategory = (Categorie) adapterView.getItemAtPosition(position);
                        int categorieId = selectedCategory.getNumero();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        // Gestion de l'événement lorsque rien n'est sélectionné dans le Spinner
                    }
                });
            }
            public void onFailure(Call call, IOException e) {
                Log.d("Test", "erreur!!! connexion impossible");
            }
        });
    }
}
