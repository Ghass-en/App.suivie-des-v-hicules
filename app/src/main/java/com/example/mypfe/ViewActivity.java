package com.example.mypfe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



import com.google.android.gms.tasks.OnSuccessListener;



import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ViewActivity extends AppCompatActivity {

    private ImageView imageView;
    TextView textView;
    Button btnDelete;
    DatabaseReference ref, DataRef;
    StorageReference StorageRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        imageView = findViewById(R.id.image_single_view_Activity);
        textView = findViewById(R.id.textview_single_view_activity);
        btnDelete = findViewById(R.id.btnDelete);
        ref = FirebaseDatabase.getInstance().getReference().child("Car");



        String Carkey = getIntent().getStringExtra("Carkey");
        DataRef = FirebaseDatabase.getInstance().getReference().child("Car").child(Carkey);
        StorageRef = FirebaseStorage.getInstance().getReference().child("CarImage").child(Carkey + ".jpg");

        ref.child(Carkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String carName = snapshot.child("CarName").getValue().toString();
                    String ImageUrl = snapshot.child("ImageUrl").getValue().toString();
                    Picasso.get().load(ImageUrl).into(imageView);
                    textView.setText(carName);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        StorageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                startActivity(new Intent(getApplicationContext(), Listofcars.class));


                            }
                        });

                    }

                });



            }


        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewActivity.this,MainActivity.class));
            }
        });

    }

}


     