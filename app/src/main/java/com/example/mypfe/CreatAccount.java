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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreatAccount extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;
    TextView btn;
    private EditText inputUsername, inputPassword, inputEmail, inputConformPassword;
    Button btnRegister;

    private FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_account);

        btn = findViewById(R.id.alreadyHaveAccount);
        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputpassword);
        inputEmail = findViewById(R.id.inputEmail);
        inputConformPassword = findViewById(R.id.inputConformpasword);
        btnRegister=findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCrededentials();
            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreatAccount.this,Connexion.class));
            }
        });

    }

    private void checkCrededentials() {
        String username=inputUsername.getText().toString();
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();
        String conformPassword=inputConformPassword.getText().toString();

        if(username.isEmpty() || username.length()<7)
        {
            showError(inputUsername,"Your username is not Valid");
        }
        else if (email.isEmpty() || !email.contains("@"))
        {
            showError(inputEmail,"Email is not Valid");
        }
        else if (password.isEmpty() || password.length()<7)
        {
            showError(inputPassword,"Password must be 7 character");
        }
        else if(conformPassword.isEmpty() || !conformPassword.equals(password))
        {
            showError(inputPassword,"Password not much!");
        }
        else
        {
            mLoadingBar.setTitle("Registration");
            mLoadingBar.setMessage("Please wait while we check your credentials");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    FirebaseUser user =mAuth.getCurrentUser();
                  if (task.isSuccessful())
                  {
                      Toast.makeText(CreatAccount.this,"Successfully Registration",Toast.LENGTH_SHORT).show();

                      DocumentReference df = fStore.collection("Users").document(user.getUid());
                      Map<String, Object> userinfo = new HashMap<>();
                      userinfo.put("Username", inputUsername.getText().toString());
                      userinfo.put("UserEmail",inputEmail.getText().toString());
                      // specify if user is admin
                      userinfo.put("isUser",1);

                      df.set(userinfo);

                  }
                  else {
                      Toast.makeText(CreatAccount.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                  }
                }
            });
        }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}