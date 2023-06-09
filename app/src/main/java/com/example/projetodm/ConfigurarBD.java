package com.example.projetodm;

import com.google.firebase.auth.FirebaseAuth;

public class ConfigurarBD {

    private static  FirebaseAuth auth;

    public static FirebaseAuth firebaseautenticacao(){

        if (auth == null){
            auth=FirebaseAuth.getInstance();
        }
        return auth;

    }
}
