package com.example.restauyou;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

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

//      Componanet Intialization
        TextView tvSignup = findViewById(R.id.tvSignup);
        TextView tvGuestLogin = findViewById(R.id.tvGuestLogin);
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassWord = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

//      Login Button Listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etUsername.getText().toString();
                String passWord = etPassWord.getText().toString();

                Toast.makeText(LoginActivity.this, String.format("%s%s", userName, passWord), Toast.LENGTH_SHORT ).show();
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