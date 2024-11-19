package com.example.lamphitryon;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ModifierPlatServiceActivity extends AppCompatActivity {
    private int idPlat;

    private int idService;
    private String date_service;
    private String quantiteProposee;
    private String quantiteVendue;
    private String prixVente;
    private String nomPlat;
    private TextView textNomPlat;
    private EditText textQuantiteProposee;
    private EditText textQuantiteVendue;
    private EditText textPrixVente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_plat_service);

        textQuantiteProposee = findViewById(R.id.editQuantiteProposee);
        textQuantiteVendue = findViewById(R.id.editTextQuantiteVendue);
        textPrixVente = findViewById(R.id.editTextPrixVente);
        textNomPlat = findViewById(R.id.textIdentification);

        idPlat = Integer.valueOf(getIntent().getStringExtra("IDPLAT"));
        idService = Integer.valueOf(getIntent().getStringExtra("IDSERVICE"));
        date_service = getIntent().getStringExtra("DATE_SERVICE");
        quantiteProposee = getIntent().getStringExtra("QUANTITEPROPOSEE");
        quantiteVendue = getIntent().getStringExtra("QUANTITEVENDUE");
        prixVente = getIntent().getStringExtra("PRIXVENTE");
        nomPlat = getIntent().getStringExtra("NOMPLAT");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textNomPlat.setText(nomPlat);
                textQuantiteProposee.setText(quantiteProposee);
                textQuantiteVendue.setText(quantiteVendue);
                textPrixVente.setText(prixVente);
            }
        });


        Button buttonModifierPlat = findViewById(R.id.ModifierPlat);
        buttonModifierPlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newQuantiteProposee = textQuantiteProposee.getText().toString();
                String newQuantiteVendue = textQuantiteVendue.getText().toString();
                String newPrixVente = textPrixVente.getText().toString();
                if (!TextUtils.isEmpty(textQuantiteProposee.getText()) && !TextUtils.isEmpty(textQuantiteVendue.getText()) && !TextUtils.isEmpty(textPrixVente.getText())) {
                    modifierPlatService(idPlat,idService,date_service,newQuantiteProposee, newQuantiteVendue, newPrixVente);
                }else{
                    Toast.makeText(ModifierPlatServiceActivity.this, "Veuillez remplir les champs pour modifier le service du plat", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button buttonQuitterModifierPlat = findViewById(R.id.buttonQuitterModifierPlat);
        buttonQuitterModifierPlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    private void modifierPlatService(int idPlat, int idService, String date_service,String newQuantiteProposee,String newQuantiteVendue,String newPrixVente) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("IDPLAT", String.valueOf(idPlat))
                .add("IDSERVICE",String.valueOf(idService))
                .add("DATE_SERVICE",date_service )
                .add("QUANTITEPROPOSEE", newQuantiteProposee )
                .add("QUANTITEVENDUE", newQuantiteVendue )
                .add("PRIXVENTE", newPrixVente )
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.144/projet_perso/Amphitryon_application_android/php/controleurs/modifierPlatService.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @SuppressLint("LongLogTag")
            public  void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {
                    Log.d("ModifierPlatServiceActivity", "Service du Plat modifié en base de données");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ModifierPlatServiceActivity.this, "Service du Plat modifié avec succès", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Log.d("ModifierPlatServiceActivity", "Erreur lors de la modification du service du plat en base de données");
                }
            }

            public void onFailure(Call call, IOException e)
            {
                Log.e("ModifierPlatActivity", "Erreur de connexion lors de la modification du service du plat à la base de données", e);
            }

        });
    }
}