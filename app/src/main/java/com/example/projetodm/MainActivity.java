package com.example.projetodm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    EditText eduseremail,edpassword;
    Button btnlogin;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarLogin();
       auth = ConfigurarBD.firebaseautenticacao();

    }

    public  void validarAutenticacao(View view){

        //String email = ((EditText)findViewById(R.id.eduseremail)).getText().toString();

        String email = eduseremail.getText().toString();
        String senha = edpassword.getText().toString();

        if(!email.isEmpty()){
            if (!senha.isEmpty()){

                User user = new User();

                user.setEmail(email);
                user.setSenha(senha);

                logar(user);
            }else{
                Toast.makeText(this,"Preencher a senha",Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void logar(User user) {

        auth.signInWithEmailAndPassword(
                user.getEmail(),user.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirHome();
                }else{
                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch(FirebaseAuthUserCollisionException e){
                        excecao = "User não está cadastrado";
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        excecao = "Email ou senha incorretos";
                    }catch(Exception e){
                        excecao="Erro ao logar o user";
                        e.printStackTrace();
                    }
                    Toast.makeText(MainActivity.this, excecao,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void abrirHome(){

        String userId = auth.getUid();

        Intent i = new Intent(MainActivity.this,HomeActivity.class);
        i.putExtra("userid", userId);
        startActivity(i);
    }

    public void paginautilizador(View view) {

        Intent in = new Intent(this,MainActivity2.class);
        startActivity(in);

    }

    protected void onStart(){
        super.onStart();
        FirebaseUser userAuth = auth.getCurrentUser();
        if(userAuth !=null){
            abrirHome();
        }
    }

    private void inicializarLogin() {
        eduseremail = findViewById(R.id.eduseremail);
        edpassword = findViewById(R.id.edpassword);
        btnlogin = findViewById(R.id.btnlogin);
    }

}