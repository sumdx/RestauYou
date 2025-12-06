package com.example.restauyou;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        Get user instance
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else {

//            Checking user Role from the database
            try {
                String uid = user.getUid();
                db = FirebaseFirestore.getInstance();
                db.collection("users").document(uid).get().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("Firestore", "Error fetching user", task.getException());
                        Toast.makeText(MainActivity.this, "Error loading user", Toast.LENGTH_SHORT).show();
                        return;
                    }
//                  Check the task before proceeding to avoid error
                    if (isFinishing() || isDestroyed()) return;

                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        String userRole = document.getString("role");
                        Intent intent;

//                      Redirect based on user role
                        switch (Objects.requireNonNull(userRole).toLowerCase()) {
                            case "admin":
                                intent = new Intent(MainActivity.this, AdminHomePageActivity.class);
                                break;
                            case "employee":
                                intent = new Intent(MainActivity.this, EmployeeHomePageActivity.class);
                                break;
                            default:
                                intent = new Intent(MainActivity.this, CustomerHomePageActivity.class);
                                break;
                        }

                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                Log.e("Firestore", "Unexpected error", e);
            }
        }
    }
}
