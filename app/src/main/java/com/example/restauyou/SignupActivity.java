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

import com.example.restauyou.ModelClass.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


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
        db = FirebaseFirestore.getInstance();
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
                String email = etEmail.getText().toString().trim();
                String password = etPassWord.getText().toString().trim();
                String name = etName.getText().toString().trim();
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
                            if (task.isSuccessful()) {

//                              New way
                                User userData = new User(name, email, "user");

//                              previous way
//                                Map<String, Object> userData = new HashMap<>();
//                                userData.put("name", name);
//                                userData.put("email", email);
//                                userData.put("role", "user");
                                FirebaseUser user = mAuth.getCurrentUser();

                                String uid = user.getUid();
                                Toast.makeText(SignupActivity.this, "Authentication Successful.",
                                        Toast.LENGTH_SHORT).show();
                                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                                user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){

                                            db.collection("users").document(uid).set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Intent userHomeIntent = new Intent(SignupActivity.this, CustomerHomePageActivity.class);
                                                        startActivity(userHomeIntent);
                                                    }else{
                                                        Log.w("ERROR-66", "updateToDatabase:failure", task.getException());
                                                        Toast.makeText(SignupActivity.this, "Profile update to database failed.",Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            });

//                                            db.collection("users").add(userData).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<DocumentReference> task) {
//                                                    if (task.isSuccessful()){
//                                                        Intent userHomeIntent = new Intent(SignupActivity.this, UserHomePageActivity.class);
//                                                        startActivity(userHomeIntent);
//                                                    }else {
//                                                        Log.w("ERROR-66", "updateToDatabase:failure", task.getException());
//                                                        Toast.makeText(SignupActivity.this, "Profile update to database failed.",
//                                                                Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }
//                                            });

                                        }else {
                                            Log.w("ERROR-66", "updateProfile:failure", task.getException());
                                            Toast.makeText(SignupActivity.this, "Profile update failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                            } else {
                                Log.w("ERROR-66", "signInWithEmail:failure", task.getException());
                                Toast.makeText(SignupActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }
            }
        });

        //  Guest login text listener
        tvGuestLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guestIntent = new Intent(SignupActivity.this, CustomerHomePageActivity.class);
                startActivity(guestIntent);
            }
        });

    }


}