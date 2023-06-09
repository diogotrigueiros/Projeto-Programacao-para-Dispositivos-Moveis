package com.example.projetodm;

import static android.content.ContentValues.TAG;
import static android.graphics.Insets.add;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String userid;

    ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result->{
                Intent resultado = result.getData();
                Bundle extras = resultado.getExtras();
                Bitmap imagem = (Bitmap)extras.get("data");

                ImageView img = findViewById(R.id.imgView);

                ImageView novoImgV = new ImageView(this);
                /*LinearLayout lnGaleria = findViewById(R.id.lnGaleria);
                lnGaleria.addView(novoImgV);*/
                Glide.with(this).load(imagem).into(novoImgV);

                Glide.with(this).load(imagem).into(img);
            });
    ActivityResultLauncher<Intent> fileLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result->{
                Intent res = result.getData();
                if(res != null && res.getData() != null)
                {
                    Uri caminhoParaoFicheiro = res.getData();
                    try
                    {
                        InputStream stream = getContentResolver().openInputStream(caminhoParaoFicheiro);
                        Bitmap imagem = BitmapFactory.decodeStream(stream);

                        ImageView img = findViewById(R.id.imgView);
                        Glide.with(this).load(imagem).into(img);
                    }
                    catch (FileNotFoundException e)
                    {
                        //throw new RuntimeException(e);
                        Log.d("MDlog", "No pic");
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent in = getIntent();
        userid = (String) in.getStringExtra("userid");

        insertdata();

    }

    private void insertdata() {

        Map<String, Object> user = new HashMap<>();
        user.put("nome","");
        user.put("password","");
        user.put("email","");
        user.put("avatar","");
        user.put("bio","");
        user.put("friends","");
        user.put("id",userid);

        db.collection("user")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(ProfileActivity.this, "Sucessful", Toast.LENGTH_SHORT);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this, "Failed", Toast.LENGTH_SHORT);
                    }
                });


    }


    public void chamarCamera(View view) {
        requestPermissions(new String[] { android.Manifest.permission.CAMERA},1);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
        {
            // Permission is not granted
            Log.d("MDLog", "failed");
        }
        else
        {
            Intent inCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(inCamera);
        }
    }

    public void pesquisarFicheiro(View view) {
        Intent inFile = new Intent(Intent.ACTION_PICK);
        inFile.setType("image/*");
        fileLauncher.launch(inFile);
    }
}