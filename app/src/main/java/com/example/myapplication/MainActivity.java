package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private TextView signup;

   private FireBaseHandler f = new FireBaseHandler(FirebaseAuth.getInstance(),this);


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signup=findViewById(R.id.sign_up);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Register.class));
                finish();
            }
        });
    }

    public void onClick(View view) {

        TextInputEditText emailInput = findViewById(R.id.email);
        TextInputEditText passInput = findViewById(R.id.password);

        String email = String.valueOf(emailInput.getText());
        String password = String.valueOf(passInput.getText());

        f.signIn(email, password);
    }

}