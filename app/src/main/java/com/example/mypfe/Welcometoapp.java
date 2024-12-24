package com.example.mypfe;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Welcometoapp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcometoapp);
        //rediger vers la page principale "Connexion" aprés 3 secondes.
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //demarrer une page
                Intent intent =new Intent(getApplicationContext(),Connexion.class );
                startActivity(intent);
                finish();
            }
        };
        // handler post delayed
        new Handler().postDelayed(runnable,3000);
    }
}

