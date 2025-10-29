package com.example.restauyou;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Componanet Intialization

        TextView tvLogin = findViewById(R.id.tvLogin);
        TextView tvGuestLogin = findViewById(R.id.tvGuestLogin);
        EditText etName = findViewById(R.id.etname);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassWord = findViewById(R.id.etPassword);
        Button btnSignUp = findViewById(R.id.btnSignup);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

//      signup process
        btnSignUp.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassWord.getText().toString();
                String name = etName.getText().toString();
                if(email.isEmpty()){
                    etEmail.setError(email);
                }
                if (password.isEmpty()) {
                    etPassWord.setError("Please fill up this field");
                }
                if (name.isEmpty()) {
                    etName.setError("Please fill up this field");
                }else {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(SignupActivity.this, "Authentication Successful.",
                                        Toast.LENGTH_SHORT).show();
                                Intent userHomeIntent = new Intent(SignupActivity.this, UserHomePageActivity.class);
                                startActivity(userHomeIntent);
                            }else {
                                Toast.makeText(SignupActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }


}