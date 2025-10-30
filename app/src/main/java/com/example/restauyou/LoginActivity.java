package com.example.restauyou;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Componanet Intialization
        TextView tvSignup = findViewById(R.id.tvSignup);
        TextView tvGuestLogin = findViewById(R.id.tvGuestLogin);
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassWord = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        // Login Button Listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etUsername.getText().toString();
                String passWord = etPassWord.getText().toString();

                mAuth.signInWithEmailAndPassword(userName,passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isComplete()){
                            Intent userHomeIntent = new Intent(LoginActivity.this, UserHomePageActivity.class);
                            startActivity(userHomeIntent);
                        }else{
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

//      Signup text Button Listener
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(signupIntent);
            }
        });
//      Guest login text listener
        tvGuestLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guestIntent = new Intent(LoginActivity.this, UserHomePageActivity.class);
                startActivity(guestIntent);


            }
        });
    }
}