package com.example.lamphitryon;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONException;
import org.json.JSONObject;

public class MenuChefCuisinier extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chefcuisinier);

        try {
            final JSONObject utilisateur = new JSONObject(getIntent().getStringExtra("utilisateur"));

            final TextView textIdentification = findViewById(R.id.textIdentification);
            String texteIdentification = utilisateur.getString("NOM") + " " + utilisateur.getString("PRENOM");
            textIdentification.setText(texteIdentification);

            final Button btnLesPlats = findViewById(R.id.btnLesPlats);
            btnLesPlats.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuChefCuisinier.this, PlatsActivity.class);
                    startActivity(intent);

                }
            });

            final Button btnLesServices = findViewById(R.id.btnLesServices);
            btnLesServices.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuChefCuisinier.this, ServicesActivity.class);
                    startActivity(intent);

                }
            });

            final Button buttonQuitterMenuChefCuisinier = (Button) findViewById(R.id.buttonQuitterMenuChefCuisinier);
            buttonQuitterMenuChefCuisinier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MenuChefCuisinier.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
            });


        } catch (JSONException e) {
            Toast.makeText(MenuChefCuisinier.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("testaffichage",e.getMessage());
        }
    }

}