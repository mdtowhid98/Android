package com.towhid.firebasedata;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText editText, editTextId;
    Button button, button2, btnUpdate, btnDelete;
    DatabaseReference databaseReference;
    TextView textView, textView2;

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

        editText = findViewById(R.id.editText);
        editTextId = findViewById(R.id.editTextId);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("MyDB");
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textViewName);

        button.setOnClickListener(v -> addUser());
        button2.setOnClickListener(v -> readUser());
        btnUpdate.setOnClickListener(v -> updateUser());
        btnDelete.setOnClickListener(v -> deleteUser());
    }

    private void addUser() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int newId = 1; // Start from 1

                // Check if snapshot is not empty
                if (snapshot.exists()) {
                    // Get existing IDs and find the highest
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        HashMap<String, Object> userMap = (HashMap<String, Object>) userSnapshot.getValue();
                        if (userMap != null) {
                            String idStr = (String) userMap.get("id");
                            if (idStr != null) { // Ensure idStr is not null
                                try {
                                    int currentId = Integer.parseInt(idStr); // Ensure this is parsed correctly
                                    if (currentId >= newId) {
                                        newId = currentId + 1; // Increment ID
                                    }
                                } catch (NumberFormatException e) {
                                    Toast.makeText(MainActivity.this, "Error parsing ID", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }

                // Now proceed to add new user with newId
                String name = editText.getText().toString().trim();

                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("id", String.valueOf(newId)); // Store as String for consistency
                hashMap.put("name", name);

                databaseReference.child("User" + newId).setValue(hashMap)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(MainActivity.this, "User added successfully", Toast.LENGTH_SHORT).show();
                            editText.setText("");
                        })
                        .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Failed to add user: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void readUser() {
        String idString = editTextId.getText().toString();

        if (idString.isEmpty()) {
            Toast.makeText(MainActivity.this, "ID cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseReference.child("User" + idString).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> map = (HashMap<String, Object>) snapshot.getValue();
                    Object id = map.get("id");
                    String name = (String) map.get("name");

                    textView.setText(String.valueOf(id));
                    textView2.setText(name);
                } else {
                    Toast.makeText(MainActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUser() {
        String idString = editTextId.getText().toString();
        String name = editText.getText().toString();

        if (idString.isEmpty() || name.isEmpty()) {
            Toast.makeText(MainActivity.this, "ID and Name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", name);

        databaseReference.child("User" + idString).updateChildren(hashMap)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(MainActivity.this, "Update data successfully", Toast.LENGTH_SHORT).show();
                    editText.setText("");
                    editTextId.setText("");
                })
                .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Data not updated", Toast.LENGTH_SHORT).show());
    }

    private void deleteUser() {
        String idString = editTextId.getText().toString();

        if (idString.isEmpty()) {
            Toast.makeText(MainActivity.this, "ID cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseReference.child("User" + idString).removeValue()
                .addOnSuccessListener(unused -> Toast.makeText(MainActivity.this, "Data deleted successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Failed to delete data", Toast.LENGTH_SHORT).show());
    }
}
