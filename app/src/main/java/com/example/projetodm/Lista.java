package com.example.projetodm;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projetodm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.ktx.Firebase;

public class Lista extends AppCompatActivity {

    private FirebaseAuth auth;
    String user;

    private CollectionReference collectionRef;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        Intent i = getIntent();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("notas");
        collectionRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            LinearLayout lnLista = findViewById(R.id.lnLista);

            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                String data = documentSnapshot.getString("Nota");

                TextView textView = new TextView(this);
                textView.setText(data);

                textView.setWidth(lnLista.getWidth());
                textView.setPadding(20, 20, 20, 20);
                textView.setBackgroundColor(Color.GREEN);
                textView.setTextSize(20);

                lnLista.addView(textView);

                textView.setOnClickListener(view -> {



                });
            }
        });

    }

    public void profile(View view) {
        Intent in = new Intent(this, ProfileActivity.class);
        in.putExtra("userid", user);
        startActivity(in);
    }

    public void deslogar(View view) {
        try {
            auth.signOut();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    }

