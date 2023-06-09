package com.example.projetodm;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

public class UserAutenticado {

    public static FirebaseUser userlogado(){
        FirebaseAuth user = ConfigurarBD.firebaseautenticacao();
        return user.getCurrentUser();
    }

}
