package com.example.myapplication;


import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FireBaseHandler {

    private FirebaseAuth auth;
    private Context context;

    public FireBaseHandler(FirebaseAuth auth, Context context)
    {
        this.auth = auth;
        this.context = context;
    }

    public void signIn(String email, String password)
    {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(context, "sign in successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context.getApplicationContext(), HomePage.class);
                    context.startActivity(intent);


                }
                else
                    Toast.makeText(context, "sign in failed!", Toast.LENGTH_SHORT).show();

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void register(String email, String password){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                    context.startActivity(intent);

                }
                else{
                    Toast.makeText(context, "Registered failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public FirebaseAuth getAuth(){return auth;}
    public  void setAuth(FirebaseAuth auth){this.auth = auth;}
}