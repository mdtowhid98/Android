package com.towhid.googlemap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private static final int RC_SIGN_IN = 1001;

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

        mAuth = FirebaseAuth.getInstance();
        oneTapClient = Identity.getSignInClient(this);
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            updateUI(currentUser);
        } else {
            signInRequest = BeginSignInRequest.builder()
                    .setGoogleIdTokenRequestOptions(
                            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                    .setSupported(true)
                                    .setServerClientId(getString(R.string.Key))
                                    .setFilterByAuthorizedAccounts(false)
                                    .build()

                    ).build();
            findViewById(R.id.googleSignInButton).setOnClickListener(v -> signIn());
        }

    }

    public void signIn() {
        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this, result -> {
                    try {
                        startIntentSenderForResult(result.getPendingIntent().getIntentSender(),
                                RC_SIGN_IN, null, 0, 0, 0);
                    } catch (Exception e) {
                        Log.e("Google Sign In", "Error Sign In", e);
                    }
                })
                .addOnFailureListener(this, e -> {
                    Toast.makeText(this, "Google Sign In failed", Toast.LENGTH_SHORT).show();
                    Log.e("Google Sign In", "Sign In failed", e);
                });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            try {
                 SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                String idToken = credential.getGoogleIdToken();
                if (idToken != null) {
                    firebaseAuthWithGoogle(idToken);
                }
            } catch (ApiException e) {
                Log.e("Google Sign In", "Sign In failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken){

        AuthCredential credential= GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this,task ->{
                    if (task.isSuccessful()){
                        FirebaseUser user=mAuth.getCurrentUser();
                        updateUI(user);
                    }else {
                        Toast.makeText(this, "Firebase Authentication Failed", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });

    }

    private void updateUI(FirebaseUser user){
        if(user !=null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

}