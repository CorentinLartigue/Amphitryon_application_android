package com.example.lamphitryon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    String responseStr;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button buttonValiderAuthentification = (Button)findViewById(R.id.buttonValider);
        buttonValiderAuthentification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    authentification();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        final Button buttonQuitterAuthentification = (Button) findViewById(R.id.buttonQuitter);
        buttonQuitterAuthentification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.this.finish();
            }
        });
    }

    public void authentification() throws IOException {

        final EditText textLogin = findViewById(R.id.editTextEmail);
        final EditText textMdp = findViewById(R.id.editTextMdp);

        RequestBody formBody = new FormBody.Builder()
                .add("LOGIN", textLogin.getText().toString())
                .add("MDP",  textMdp.getText().toString())
                .build();

        Request request = new Request.Builder()
                .url("http://10.100.0.6/~ylesgourgues/php/controleurs/authentification.php")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public  void onResponse(Call call, Response response) throws IOException {

                responseStr = response.body().string();
                Log.d("Test",responseStr);

                if (responseStr.compareTo("false")!=0){
                    try {
                        JSONObject utilisateur = new JSONObject(responseStr);
                        Log.d("Test",utilisateur.getString("LOGIN") + " est  connecté");
                        if(utilisateur.getString("IDSTATUT").compareTo("2")==0) {
                            Intent intent = new Intent(MainActivity.this, MenuChefCuisinier.class);
                            intent.putExtra("UTILISATEUR", utilisateur.toString());
                            startActivity(intent);
                        }
                        else if(utilisateur.getString("IDSTATUT").compareTo("1")==0) {
                            Intent intent = new Intent(MainActivity.this, MenuServeur.class);
                            intent.putExtra("UTILISATEUR", utilisateur.toString());
                            startActivity(intent);
                        }
                    }
                    catch(JSONException e){
                        // Toast.makeText(MainActivity.this, "Erreur de connexion !!!! !", Toast.LENGTH_SHORT).show();
                        Log.d("Test",e.getMessage());
                    }
                } else {
                    Log.d("Test","Login ou mot de  passe non valide !");
                }
            }

            public void onFailure(Call call, IOException e)
            {
                Log.d("Test","erreur!!! connexion impossible");
            }

        });
    }
}