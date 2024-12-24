package com.example.mypfe;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Connexion extends AppCompatActivity {
    TextView btn;
    EditText inputEmail,inputPassword;
    Button btnLogin;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        btn = findViewById(R.id.textViewSignUp);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputpassword);
        btnLogin = findViewById(R.id.btnlogin);
        mAuth= FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        mLoadingBar=new ProgressDialog(Connexion.this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCrededentials();
            }
            private void checkCrededentials() {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                if (email.isEmpty() || !email.contains("@")) {
                    showError(inputEmail, "Email not valid");
                } else if (password.isEmpty() || password.length() < 7) {
                    showError(inputPassword, "Password must be 7 characters");
                } else {
                    mLoadingBar.setTitle("Login");
                    mLoadingBar.setMessage("Please wait while we check your credentials");
                    mLoadingBar.setCanceledOnTouchOutside(false);
                    mLoadingBar.show();

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                AuthResult authResult = task.getResult();

                                Toast.makeText(Connexion.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                                checkUserAccessLevel(authResult.getUser().getUid());


                                mLoadingBar.dismiss();
                            } else {
                                Toast.makeText(Connexion.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                mLoadingBar.dismiss();
                            }
                        }
                    });
                }
            }

            private  void showError(EditText input, String s){
                input.setError(s);
                input.requestFocus();
            }

        });




        TextView btn = findViewById(R.id.textViewSignUp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Connexion.this, CreatAccount.class));
            }
        });


    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    if (documentSnapshot.contains("isAdmin")) {
                        startActivity(new Intent(Connexion.this, Listofcars.class));
                        finish();
                    } else if (documentSnapshot.contains("isUser")) {
                        startActivity(new Intent(Connexion.this, Listofcars.class));
                        finish();
                    }
                } else {
                    Toast.makeText(Connexion.this, "User does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}