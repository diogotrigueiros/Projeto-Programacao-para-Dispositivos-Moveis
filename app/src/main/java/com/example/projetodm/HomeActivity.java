package com.example.projetodm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private LinearLayout lnNotas;

    private CollectionReference collectionRef;
    FirebaseFirestore db;

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = ConfigurarBD.firebaseautenticacao();

        Intent in = getIntent();
        user = (String) in.getStringExtra("userid");
    }



    public void deslogar(View view){
        try{
            auth.signOut();
            finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void profile (View view){
        Intent in = new Intent(this,ProfileActivity.class);
        in.putExtra("userid", user);
        startActivity(in);
    }

    public void adicionarNota(View v) {
        db = FirebaseFirestore.getInstance();
        EditText edNota = findViewById(R.id.edNota);

        String Nota = edNota.getText().toString();

        Map<String, Object> nota = new HashMap<>();
        nota.put("Nota", Nota);
        db.collection("notas")
                .add(nota)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(HomeActivity.this, "Nota adicionada", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HomeActivity.this, "Nota não adicionada", Toast.LENGTH_SHORT).show();
                    }
                });

        edNota.setText("");

        lnNotas = findViewById(R.id.lnNotas);

        TextView txtNota = new TextView(getApplicationContext());
        txtNota.setText(Nota);

        txtNota.setWidth(lnNotas.getWidth());
        txtNota.setPadding(20,20,20,20);
        txtNota.setBackgroundColor(Color.GREEN);
        txtNota.setTextSize(20);

        lnNotas.addView(txtNota);
    }


    public void verNotas(View view) {

    Intent in = new Intent(this,Lista.class);
    startActivity(in);

    }











}