package com.example.projetodm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity2 extends AppCompatActivity {


    User user;
    FirebaseAuth autenticacao;
    EditText edNome, edEmail, edSenha;
    Button botaoCriar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent in = getIntent();

        inicializar();
    }

    private void inicializar() {

        edNome = findViewById(R.id.edusername);
        edEmail = findViewById(R.id.edemail);
        edSenha = findViewById(R.id.edpassword);
        botaoCriar = findViewById(R.id.criarconta);

    }

    public void validarCampos(View v) {
        String nome = edNome.getText().toString();
        String email = edEmail.getText().toString();
        String senha = edSenha.getText().toString();

        if (!email.isEmpty()) {
            if (!nome.isEmpty()) {
                if (!senha.isEmpty()) {

                    user = new User();

                    user.setUsername(nome);
                    user.setEmail(email);
                    user.setSenha(senha);

                    criarUser();

                } else {
                    Toast.makeText(this, "Preecha a senha", Toast.LENGTH_SHORT).show();

                }

            } else {
                Toast.makeText(this, "Preecha o email", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(this, "Preecha o nome", Toast.LENGTH_SHORT).show();
        }
    }


    private void criarUser() {

        autenticacao = ConfigurarBD.firebaseautenticacao();

        autenticacao.createUserWithEmailAndPassword(
                user.getEmail(), user.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity2.this, "Sucesso ao registar user", Toast.LENGTH_SHORT).show();
                } else {
                    String excecao = "";

                    try {
                        throw  task.getException();
                    }catch(FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao ="Digite um email válido";
                    }catch (FirebaseAuthUserCollisionException e){
                        excecao="Esta conta já existe";
                    }catch (Exception e){
                        excecao="Erro ao cadastrar utilizador"+e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(MainActivity2.this, excecao, Toast.LENGTH_SHORT).show();
                }


            edNome = findViewById(R.id.edusername);
            edEmail = findViewById(R.id.edemail);
            edSenha = findViewById(R.id.edpassword);
            botaoCriar = findViewById(R.id.criarconta);

            edNome.setText("");
            edEmail.setText("");
            edSenha.setText("");




            }

        });

    }
}

