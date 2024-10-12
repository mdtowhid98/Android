package com.towhid.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import de.hdodenhof.circleimageview.CircleImageView;

public class Registration extends AppCompatActivity {

    TextView loginbut;
    EditText rg_username, rg_email, rg_password, rg_repassword;
    Button rg_signup;
    CircleImageView rg_profileImg;
    FirebaseAuth auth;
    Uri imageURI;
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog progressDialog;

    private static final String DEFAULT_PROFILE_IMAGE = "https://firebasestorage.googleapis.com/v0/b/chat-app-949d3.appspot.com/o/man.png?alt=media&token=dbcfcbf8-7285-4b26-bc46-9e7a7a337ccd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Establishing The Account");
        progressDialog.setCancelable(false);
        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        initializeViews();

        loginbut.setOnClickListener(v -> {
            Intent intent = new Intent(Registration.this, Login.class);
            startActivity(intent);
            finish();
        });

        rg_signup.setOnClickListener(v -> registerUser());

        rg_profileImg.setOnClickListener(v -> selectProfileImage());
    }

    private void initializeViews() {
        loginbut = findViewById(R.id.loginbut);
        rg_username = findViewById(R.id.rgusername);
        rg_email = findViewById(R.id.rgemail);
        rg_password = findViewById(R.id.rgpassword);
        rg_repassword = findViewById(R.id.rgrepassword);
        rg_profileImg = findViewById(R.id.profilerg0);
        rg_signup = findViewById(R.id.signupbutton);
    }

    private void registerUser() {
        String namee = rg_username.getText().toString().trim();
        String emaill = rg_email.getText().toString().trim();
        String Password = rg_password.getText().toString();
        String cPassword = rg_repassword.getText().toString();

        if (TextUtils.isEmpty(namee) || TextUtils.isEmpty(emaill) ||
                TextUtils.isEmpty(Password) || TextUtils.isEmpty(cPassword)) {
            showToast("Please Enter Valid Information");
            return;
        }
        if (!isValidEmail(emaill)) {
            rg_email.setError("Type A Valid Email Here");
            return;
        }
        if (Password.length() < 6) {
            rg_password.setError("Password Must Be 6 Characters Or More");
            return;
        }
        if (!Password.equals(cPassword)) {
            rg_password.setError("The Password Doesn't Match");
            return;
        }

        progressDialog.show();
        auth.createUserWithEmailAndPassword(emaill, Password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String id = task.getResult().getUser().getUid();
                if (imageURI != null) {
                    uploadProfileImage(id, namee, emaill, Password);
                } else {
                    createUserInDatabase(id, namee, emaill, Password, DEFAULT_PROFILE_IMAGE);
                }
            } else {
                showToast(task.getException().getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private void uploadProfileImage(String id, String name, String email, String password) {
        StorageReference storageReference = storage.getReference().child("Upload").child(id);
        storageReference.putFile(imageURI).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    createUserInDatabase(id, name, email, password, uri.toString());
                });
            } else {
                showToast("Error uploading image");
                progressDialog.dismiss();
            }
        });
    }

    private void createUserInDatabase(String id, String name, String email, String password, String imageUrl) {
        DatabaseReference reference = database.getReference().child("user").child(id);
        Users user = new Users(id, name, email, password, imageUrl, "Hey I'm Using This Application");
        reference.setValue(user).addOnCompleteListener(task -> {
            progressDialog.dismiss();
            if (task.isSuccessful()) {
                Intent intent = new Intent(Registration.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                showToast("Error in creating the user");
            }
        });
    }

    private void selectProfileImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {
            imageURI = data.getData();
            rg_profileImg.setImageURI(imageURI);
        }
    }

    private void showToast(String message) {
        Toast.makeText(Registration.this, message, Toast.LENGTH_SHORT).show();
    }
}
